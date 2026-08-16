package org.example;

import org.example.Ergonomics;

import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.lang.ClassNotFoundException;

public class ContactSystem {
        private Connection connection;
        public boolean isConnected = false;
        private final String Prefix = Ergonomics.Color.Prefix;
        private final String Arrow = Ergonomics.Color.Arrow;
        public static DatabaseManager databaseManager;
        public void setConnection(String URL, String name , String user , String password){
                try {
                        String url = "jdbc:postgresql://"+ URL + "/" + name;
                        System.out.println(url + " " + user);
                        // Open and automatically close the connection
                        connection = DriverManager.getConnection(url, user, "3#7GHkM%7#");
                        System.out.println(Prefix + Ergonomics.Color.GREEN + "Connetced to postgres" + Ergonomics.Color.RESET);
                        isConnected=true;
                        databaseManager = new DatabaseManager(connection);

                        } catch (SQLException e) {
                                System.out.println(Ergonomics.Color.RED + "ERROR"+ Ergonomics.Color.RESET +": connection to the database failed, please check your user information");
                        e.printStackTrace();
                        }
                }
        public List<String> tableList= new ArrayList<>();
        public void getTables(){
                List<String> tables= new ArrayList<>();
                try{
                        DatabaseMetaData metaData = connection.getMetaData();
                        ResultSet Table = metaData.getTables(null, "public", "%", new String[]{"TABLE"});
                        System.out.println(Prefix + "Tables in database:");
                        while (Table.next()) {
                                tables.add(Table.getString("TABLE_NAME"));
                                System.out.println(Arrow + tables.size() + "- " + Table.getString("TABLE_NAME"));
                        }
                        System.out.println("0- Create new table");
                        tableList = tables;
                }catch (SQLException e){
                        System.out.println("ERROR:data fetching failed");
                }
        }
        }

