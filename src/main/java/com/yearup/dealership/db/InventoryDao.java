package com.yearup.dealership.db;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.SQLException;

public class InventoryDao {
    private DataSource dataSource;

    public InventoryDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addVehicleToInventory(String vin, int dealershipId) {
        // TODO: Implement the logic to add a vehicle to the inventory

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into inventory (vin, dealershipID) values (?,?)")){
            preparedStatement.setString(1,vin);
            preparedStatement.setInt(2,dealershipId);

            int rows = preparedStatement.executeUpdate();

            System.out.println("Rows inserted: " + rows);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }
    }


    public void removeVehicleFromInventory(String vin) {
        // TODO: Implement the logic to remove a vehicle from the inventory
    }
}
