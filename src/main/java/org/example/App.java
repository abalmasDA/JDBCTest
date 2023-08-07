package org.example;

import java.sql.*;

/**
 * Використовуючи IntelijIdea та JDBC, зробіть вибірку за допомогою JOIN's для таких завдань:
 * 1) Отримайте контактні дані співробітників (номери телефонів, місце проживання).
 * 2) Отримайте інформацію про дату народження всіх неодружених співробітників та їх номери телефонів.
 * 3) Отримайте інформацію про всіх менеджерів компанії: дату народження та номер телефону.
 */
public class App {
    public static void main(String[] args) {
        System.out.println();
        getEmployeePersonalDetails();
        System.out.println();
        getEmployeesInfo();
        System.out.println();
        getManagersInfo();
    }

    private static Connection getConnection() throws SQLException {
        String url = "jdbc:mysql://localhost:3306/myjoinsdb";
        String username = "root";
        String password = "Dnepr19977";
        return DriverManager.getConnection(url, username, password);
    }

    public static void getEmployeePersonalDetails() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT e.NAME, e.SURNAME, e.PHONE, " +
                             "emp.ADRESSE_CITY, emp.ADRESSE_STREET, " +
                             "emp.ADRESSE_BUILDING FROM employee e " +
                             "JOIN employee_personal_details emp ON e.ID = emp.ID_EMPLOYEE")) {
            while (resultSet.next()) {
                String name = resultSet.getString("NAME");
                String surname = resultSet.getString("SURNAME");
                String phone = resultSet.getString("PHONE");
                String city = resultSet.getString("ADRESSE_CITY");
                String street = resultSet.getString("ADRESSE_STREET");
                String building = resultSet.getString("ADRESSE_BUILDING");
                System.out.println(name + " " + surname + ", " + phone + ", " + city + ", " + street + ", " + building);
            }
        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getEmployeesInfo() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT e.NAME, e.SURNAME, e.PHONE, " +
                             "emp.DATE_OF_BIRTH FROM employee e " +
                             "JOIN employee_personal_details emp ON e.ID = emp.ID_EMPLOYEE " +
                             "WHERE emp.MARITAL_STATUS = 'Single'")) {
            while (resultSet.next()) {
                String name = resultSet.getString("NAME");
                String surname = resultSet.getString("SURNAME");
                String phone = resultSet.getString("PHONE");
                String birthDate = resultSet.getString("DATE_OF_BIRTH");
                System.out.println(name + " " + surname + ", " + phone + ", " + birthDate);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }

    public static void getManagersInfo() {
        try (Connection connection = getConnection();
             Statement statement = connection.createStatement();
             ResultSet resultSet = statement.executeQuery(
                     "SELECT e.NAME, e.SURNAME, e.PHONE, " +
                             "emp.DATE_OF_BIRTH FROM employee e " +
                             "JOIN employee_personal_details emp ON e.ID = emp.ID_EMPLOYEE " +
                             "JOIN employee_working_details empl ON e.ID = empl.ID_EMPLOYEE " +
                             "WHERE empl.POSITION = 'Manager'")) {

            while (resultSet.next()) {
                String name = resultSet.getString("NAME");
                String surname = resultSet.getString("SURNAME");
                String phone = resultSet.getString("PHONE");
                String birthDate = resultSet.getString("DATE_OF_BIRTH");
                System.out.println(name + " " + surname + ", " + phone + ", " + birthDate);
            }

        } catch (SQLException e) {
            e.printStackTrace();
        }
    }
}
