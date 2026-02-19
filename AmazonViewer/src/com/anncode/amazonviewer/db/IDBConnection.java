package com.anncode.amazonviewer.db;

import java.sql.Connection;

public interface IDBConnection {

    default Connection connectToDB(){
        Connection connection = null;
        try {
            Class.forName("com.mysql.jdbc.Driver");
            Connection DriverManager.getConnecion();
        } catch (Exception e) {
            // TODO: handle exception
        } finally {

        }
    }
}
