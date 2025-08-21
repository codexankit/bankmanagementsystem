package com.helpers;

import java.sql.Connection;
import java.sql.DriverManager;

public class ConnectionUtility {

    private final static String url="jdbc:postgresql://localhost:5412/Jdbc_assignment";
    private final static String username="postgres";
    private final static String password="12345678";
    static Connection conn = null;
    public static Connection getConnection(){
        try{
            //load the driver
            Class.forName("org.postgresql.Driver");
            //create the connection
            conn = DriverManager.getConnection(url,username,password);
//            System.out.println("Connected Successfully");
        }catch(ClassNotFoundException e){
            StackTraceElement[] trace = e.getStackTrace();
            for(StackTraceElement element : trace){
                System.out.println("Exception at : "+element);
            }
        }catch (Exception e){
            StackTraceElement[] trace = e.getStackTrace();
            for(StackTraceElement element : trace){
                System.out.println("Exception at : "+element);
            }
        }
        return conn;
    }
}
