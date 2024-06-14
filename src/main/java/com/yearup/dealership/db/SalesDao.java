package com.yearup.dealership.db;

import com.yearup.dealership.models.SalesContract;

import javax.sql.DataSource;
import java.sql.*;

public class SalesDao {
    private DataSource dataSource;

    public SalesDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addSalesContract(SalesContract salesContract) {
        // TODO: Implement the logic to add a sales contract

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into sales_contracts (VIN, sale_date, price) values (?,?,?)",
                    Statement.RETURN_GENERATED_KEYS)){
            preparedStatement.setString(1, salesContract.getVin());
            preparedStatement.setDate(2, Date.valueOf(salesContract.getSaleDate()));
            preparedStatement.setDouble(3, salesContract.getPrice());



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
