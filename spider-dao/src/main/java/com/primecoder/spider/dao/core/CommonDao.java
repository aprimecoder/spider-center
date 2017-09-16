package com.primecoder.spider.dao.core;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.*;

/**
 * Created by primecoder on 2017/7/8.
 * E-mail : aprimecoder@gmail.com
 */
public class CommonDao {

    private static final DBConnection DB_CONNECTION = DBConnection.getInstance();

    private static final Logger LOGGER = LoggerFactory.getLogger(CommonDao.class);

    private static CommonDao instance = null;

    private CommonDao() {

    }

    public static synchronized CommonDao getInstance() {

        if (null == instance) {

            instance = new CommonDao();
        }

        return instance;
    }

    public boolean tableExist(String tableName) {

        Connection connection = DB_CONNECTION.get();

        try {
            DatabaseMetaData dbm = connection.getMetaData();

            ResultSet rs = dbm.getTables(null, null, tableName, null);
            if (rs.next()) {

                LOGGER.info("table : {}",tableName);
            }
            else {
                System.out.println("Not Exist");
            }

            DB_CONNECTION.put(connection);

        } catch (SQLException e) {

            LOGGER.error("table : {} exist error : {}",tableName,e.getMessage());

            DB_CONNECTION.put(connection);

        }

        return true;
    }

    private void createTable(String tableName) {

        String createTableSql = "";
    }

    public void executeInsert(String insertSql) {

        Connection connection = DB_CONNECTION.get();

        Statement stmt;
        try {

            stmt = connection.createStatement();
            stmt.executeUpdate(insertSql);

        } catch (SQLException e) {

            LOGGER.error("exception to execute sql : {}",insertSql);

        } finally {

            DB_CONNECTION.put(connection);
        }
    }

    public boolean exist(String sql) {

        Statement stmt = null;

        Connection connection = DB_CONNECTION.get();

        try {

            stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            if (res.next()) {
                int cnt = res.getInt("cnt");
                return cnt > 0;
            }

            return false;

        } catch (SQLException e) {

            LOGGER.error("execute sql : {} error : {}",sql,e.getMessage());

        } finally {

            if (null != stmt) {
                try {
                    stmt.close();
                } catch (SQLException e) {

                    LOGGER.error("execute sql : {} error : {}",sql,e.getMessage());
                }
            }

            DB_CONNECTION.put(connection);
        }

        return false;
    }


}
