package com.yearup.dealership.db;

import com.yearup.dealership.models.LeaseContract;

import javax.sql.DataSource;
import java.sql.*;

public class LeaseDao {
    private DataSource dataSource;

    public LeaseDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addLeaseContract(LeaseContract leaseContract) {
        // TODO: Implement the logic to add a lease contract

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into lease_contracts (VIN, lease_start, lease_end, monthly_payment) values (?,?,?,?)",
                    Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, leaseContract.getVin());
            preparedStatement.setDate(2, Date.valueOf(leaseContract.getLeaseStart()));
            preparedStatement.setDate(3, Date.valueOf(leaseContract.getLeaseEnd()));
            preparedStatement.setDouble(4, leaseContract.getMonthlyPayment());


            int rows = preparedStatement.executeUpdate();

            System.out.println("Rows inserted: " + rows);

            try(ResultSet keys = preparedStatement.getGeneratedKeys()){
                while(keys.next()){
                    System.out.println("A new key was added: " + keys.getInt(1));
                }
            }
        }
        catch (Exception ex){
            ex.printStackTrace();
        }


    }
}
