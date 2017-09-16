package com.primecoder.spider.dao;

import com.primecoder.spider.dao.core.CommonDao;
import com.primecoder.spider.dao.core.DBConnection;
import com.primecoder.spider.dao.entity.UrlDownloadedEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by primecoder on 2017/7/15.
 * E-mail : aprimecoder@gmail.com
 */
public class UrlDownloadedDao {

    private static UrlDownloadedDao instance = null;

    private static final CommonDao COMMON_DAO = CommonDao.getInstance();

    private static final DBConnection DB_CONNECTION = DBConnection.getInstance();

    private static final Logger LOGGER = LoggerFactory.getLogger(UrlDownloadedDao.class);


    private UrlDownloadedDao() {

    }

    public static synchronized UrlDownloadedDao getInstance() {

        if (null == instance) {

            instance = new UrlDownloadedDao();
        }

        return instance;
    }


    public void insert(UrlDownloadedEntity urlDownloadedEntity) {

        String sql = "insert into blogger_url_downloaded (blogger_name,url,file_path,type) values "
                + "('" + urlDownloadedEntity.getBloggerName() + "','" + urlDownloadedEntity.getUrl()
                + "','" + urlDownloadedEntity.getFilepath() + "'," + urlDownloadedEntity.getType() + ")";

        COMMON_DAO.executeInsert(sql);
    }

    public List<UrlDownloadedEntity> getByBloggerName(String bloggerName) {

        String sql = "select blogger_name,url,file_path,type from blogger_url_downloaded "
                + "where blogger_name = '" + bloggerName + "'";

        List<UrlDownloadedEntity> bloggerDownloadedList = new ArrayList<>();

        Statement stmt = null;
        Connection connection = DB_CONNECTION.get();

        try {

            stmt = connection.createStatement();
            ResultSet res = stmt.executeQuery(sql);
            while (res.next()) {

                UrlDownloadedEntity urlDownloadedEntity = new UrlDownloadedEntity();
                urlDownloadedEntity.setBloggerName(res.getString("blogger_name"));
                urlDownloadedEntity.setUrl(res.getString("url"));
                urlDownloadedEntity.setFilepath(res.getString("file_path"));
                urlDownloadedEntity.setType(res.getInt("type"));

                bloggerDownloadedList.add(urlDownloadedEntity);

                return bloggerDownloadedList;
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

        return bloggerDownloadedList;
    }
}
