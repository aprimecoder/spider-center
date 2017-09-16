package com.primecoder.spider.dao;

import com.primecoder.spider.dao.core.DBConnection;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

/**
 * Created by primecoder on 2017/8/17.
 * E-mail : aprimecoder@gmail.com
 */
public class BloggerInfoDao {

    private static BloggerInfoDao instance = null;

    private static final DBConnection DB_CONNECTION = DBConnection.getInstance();

    private static final Logger LOGGER = LoggerFactory.getLogger(BloggerInfoDao.class);

    private BloggerInfoDao() {

    }

    public static synchronized BloggerInfoDao getInstance() {

        if (null == instance) {
            instance = new BloggerInfoDao();
        }

        return instance;
    }

    public String getBloggerName() {

        String sql = "select blogger_name bloggerName from blogger_info where is_downloaded = false LIMIT 0,1";

        Statement stmt = null;

        Connection connection = DB_CONNECTION.get();

        try {

            stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            if (res.next()) {
                return res.getString("bloggerName");
            }

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

        return null;
    }
}
