package ru.mail.romanov1234567890987.repository.impl;

import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import ru.mail.romanov1234567890987.repository.ConnectionRepository;
import ru.mail.romanov1234567890987.util.PropertyUtil;

import java.lang.invoke.MethodHandles;
import java.sql.Connection;
import java.sql.SQLException;

import static ru.mail.romanov1234567890987.repository.constant.ConnectionConstant.*;

public class ConnectionRepositoryImpl implements ConnectionRepository {
    private static final Logger logger = LogManager.getLogger(MethodHandles.lookup().lookupClass());
    private static ConnectionRepository instance;

    private ConnectionRepositoryImpl() {
    }

    public static ConnectionRepository getInstance() {
        if (instance == null) {
            instance = new ConnectionRepositoryImpl();
        }
        return instance;
    }

    private static HikariDataSource ds;

    @Override
    public Connection getConnection() throws SQLException {
        if (ds == null) {
            try {
                Class.forName("com.mysql.jdbc.Driver");
            } catch (ClassNotFoundException e) {
                logger.error(e.getMessage(),e);
            }
            PropertyUtil propertyUtil = new PropertyUtil();
            HikariConfig config = new HikariConfig();
            config.setJdbcUrl(propertyUtil.getProperty(DATABASE_URL));
            config.setUsername(propertyUtil.getProperty(DATABASE_USERNAME));
            config.setPassword(propertyUtil.getProperty(DATABASE_PASSWORD));
            config.addDataSourceProperty(DATABASE_PREPARE_STATEMENT_CACHE_PROPERTY_NAME,
                    propertyUtil.getProperty(DATABASE_PREPARE_STATEMENT_CACHE));
            config.addDataSourceProperty(DATABASE_PREPARE_STATEMENT_CACHE_SIZE_PROPERTY_NAME,
                    propertyUtil.getProperty(DATABASE_PREPARE_STATEMENT_CACHE_SIZE));
            config.addDataSourceProperty(DATABASE_PREPARE_STATEMENT_CACHE_SQL_LIMIT_PROPERTY_NAME,
                    propertyUtil.getProperty(DATABASE_PREPARE_STATEMENT_CACHE_SQL_LIMIT));
            ds = new HikariDataSource(config);
        }
        return ds.getConnection();
    }
}


