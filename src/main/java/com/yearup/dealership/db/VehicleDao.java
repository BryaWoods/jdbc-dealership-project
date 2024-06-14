package com.yearup.dealership.db;

import com.yearup.dealership.models.Vehicle;

import javax.sql.DataSource;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;

public class VehicleDao {
    private DataSource dataSource;

    public VehicleDao(DataSource dataSource) {
        this.dataSource = dataSource;
    }

    public void addVehicle(Vehicle vehicle) {
        // TODO: Implement the logic to add a vehicle

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "insert into vehicles (VIN, make, model, year, SOLD, color, vehicleType, odometer, price) values (?,?,?,?,?,?,?,?,?)")){
            preparedStatement.setString(1, vehicle.getVin());
            preparedStatement.setString(2, vehicle.getMake());
            preparedStatement.setString(3, vehicle.getModel());
            preparedStatement.setInt(4,vehicle.getYear());
            preparedStatement.setBoolean(5, vehicle.isSold());
            preparedStatement.setString(6, vehicle.getColor());
            preparedStatement.setString(7,vehicle.getVehicleType());
            preparedStatement.setInt(8,vehicle.getOdometer());
            preparedStatement.setDouble(9,vehicle.getPrice());


            int rows = preparedStatement.executeUpdate();

            System.out.println("Rows inserted: " + rows);
        }
        catch (Exception ex){
            ex.printStackTrace();
        }

    }

    public void removeVehicle(String VIN) {
        // TODO: Implement the logic to remove a vehicle

        try(Connection connection = dataSource.getConnection();
            PreparedStatement preparedStatement = connection.prepareStatement(
                    "DELETE FROM vehicles WHERE VIN = ?")){
            preparedStatement.setString(1, VIN);

            int rows = preparedStatement.executeUpdate();
            System.out.println("Rows deleted: " + rows);

        } catch (Exception ex){
            ex.printStackTrace();
        }
    }

    public List<Vehicle> searchByPriceRange(double minPrice, double maxPrice) {
        // TODO: Implement the logic to search vehicles by price range

        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Vehicle WHERE price BETWEEN ? AND ?")) {
            statement.setDouble(1, minPrice);
            statement.setDouble(2, maxPrice);

            try (ResultSet results = statement.executeQuery()) {
                while (results.next()) {
                    String vin = results.getString("VIN");
                    String make = results.getString("make");
                    String model = results.getString("model");
                    int year = results.getInt("year");
                    boolean sold = results.getBoolean("SOLD");
                    String color = results.getString("color");
                    String vehicleType = results.getString("vehicleType");
                    int odometer = results.getInt("odometer");
                    double price = results.getDouble("price");


                    Vehicle vehicle = new Vehicle(vin,make,model,year,sold,color,vehicleType,odometer,price);
                    vehicles.add(vehicle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }


        return vehicles;
    }

    public List<Vehicle> searchByMakeModel(String make, String model) {
        // TODO: Implement the logic to search vehicles by make and model

        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Vehicle WHERE make = ? AND model = ?")) {
            statement.setString(1, make);
            statement.setString(2, model);

            try (ResultSet results = statement.executeQuery()) {
                while (results.next()) {
                    Vehicle vehicle = createVehicleFromResultSet(results);
                    vehicles.add(vehicle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public List<Vehicle> searchByYearRange(int minYear, int maxYear) {
        // TODO: Implement the logic to search vehicles by year range


        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Vehicle WHERE year BETWEEN ? AND ?")) {
            statement.setDouble(1, minYear);
            statement.setDouble(2, maxYear);

            try (ResultSet results = statement.executeQuery()) {
                while (results.next()) {
                    Vehicle vehicle = createVehicleFromResultSet(results);
                    vehicles.add(vehicle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }

        return vehicles;
    }

    public List<Vehicle> searchByColor(String color) {
        // TODO: Implement the logic to search vehicles by color

        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Vehicle WHERE color = ?")) {
            statement.setString(1, color);


            try (ResultSet results = statement.executeQuery()) {
                while (results.next()) {
                    Vehicle vehicle = createVehicleFromResultSet(results);
                    vehicles.add(vehicle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public List<Vehicle> searchByMileageRange(int minMileage, int maxMileage) {
        // TODO: Implement the logic to search vehicles by mileage range

        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Vehicle WHERE odometer BETWEEN ? AND ?")) {
            statement.setDouble(1, minMileage);
            statement.setDouble(2, maxMileage);

            try (ResultSet results = statement.executeQuery()) {
                while (results.next()) {

                    Vehicle vehicle = createVehicleFromResultSet(results);
                    vehicles.add(vehicle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    public List<Vehicle> searchByType(String type) {
        // TODO: Implement the logic to search vehicles by type

        List<Vehicle> vehicles = new ArrayList<>();

        try (Connection connection = dataSource.getConnection();
             PreparedStatement statement = connection.prepareStatement(
                     "SELECT * FROM Vehicle WHERE vehicleType = ?")) {
            statement.setString(1, type);


            try (ResultSet results = statement.executeQuery()) {
                while (results.next()) {
                    Vehicle vehicle = createVehicleFromResultSet(results);
                    vehicles.add(vehicle);
                }
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
        return vehicles;
    }

    private Vehicle createVehicleFromResultSet(ResultSet resultSet) throws SQLException {
        Vehicle vehicle = new Vehicle();
        vehicle.setVin(resultSet.getString("VIN"));
        vehicle.setMake(resultSet.getString("make"));
        vehicle.setModel(resultSet.getString("model"));
        vehicle.setYear(resultSet.getInt("year"));
        vehicle.setSold(resultSet.getBoolean("SOLD"));
        vehicle.setColor(resultSet.getString("color"));
        vehicle.setVehicleType(resultSet.getString("vehicleType"));
        vehicle.setOdometer(resultSet.getInt("odometer"));
        vehicle.setPrice(resultSet.getDouble("price"));
        return vehicle;
    }
}
