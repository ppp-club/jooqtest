package org.example;

import java.sql.*;

public class Main {

    public static void main(String[] args) {
        String url = "jdbc:hsqldb:file:mydb";
        String user = "admin";
        String password = "admin";

        try (Connection con = DriverManager.getConnection(url, user, password);
             Statement stmt = con.createStatement()) {

            // Tabelle CUSTOMER fehlt? Dann anlegen
            if (!con.getMetaData().getTables(null, null, "CUSTOMER", null).next()) {
                String[] sqlStmts = {
                        "CREATE TABLE CUSTOMER(ID INTEGER NOT NULL PRIMARY KEY,FIRSTNAME VARCHAR(255),"
                                + "LASTNAME VARCHAR(255),STREET VARCHAR(255),CITY VARCHAR(255))",
                        "INSERT INTO CUSTOMER VALUES(0,'Laura','Steel','429 Seventh Av.','Dallas')",
                        "INSERT INTO CUSTOMER VALUES(1,'Susanne','King','366 - 20th Ave.','Olten')",
                        "INSERT INTO CUSTOMER VALUES(2,'Anne','Miller','20 Upland Pl.','Lyon')"};

                for (String sql : sqlStmts) {
                    stmt.executeUpdate(sql);
                }

                System.out.println("Tabelle und Daten neu angelegt");
            }

            // Tabelle abfragen
            try (ResultSet rs = stmt.executeQuery("SELECT * FROM CUSTOMER")) {
                while (rs.next()) {
                    System.out.printf("%s, %s %s%n", rs.getString("firstname"), rs.getString("lastname"), rs.getString("street"));
                }

            }

        } catch (SQLException e) {
            throw new RuntimeException(e);
        }
    }
}