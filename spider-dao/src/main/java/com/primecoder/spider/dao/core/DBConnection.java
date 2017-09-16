package com.primecoder.spider.dao.core;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.concurrent.BlockingQueue;
import java.util.concurrent.LinkedBlockingQueue;

/**
 * Created by primecoder on 2017/7/8.
 * E-mail : aprimecoder@gmail.com
 */
public class DBConnection {

    private static DBConnection instance = null;

    private static final int CONNECTION_NUM = 100;

    private final BlockingQueue<Connection> connections = new LinkedBlockingQueue<>(CONNECTION_NUM);

    private DBConnection() {

        createConnection();
    }

    private void createConnection() {

        for (int i = 0;i < CONNECTION_NUM;i++) {

            try {
                Class.forName("com.mysql.jdbc.Driver").newInstance();
                Connection connection = DriverManager.getConnection("jdbc:mysql://127.0.0.1:3306/blogger?serverTimezone=UTC", "root", "123456");

                connections.put(connection);

            } catch (InstantiationException e) {
                e.printStackTrace();
            } catch (IllegalAccessException e) {
                e.printStackTrace();
            } catch (ClassNotFoundException e) {
                e.printStackTrace();
            } catch (SQLException e) {
                e.printStackTrace();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static DBConnection getInstance() {

        if (null == instance) {
            instance = new DBConnection();
        }

        return instance;
    }

    public Connection get() {

        try {
            return connections.take();
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        return null;
    }

    public synchronized void put(Connection connection) {

        try {
            connections.put(connection);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }
}
