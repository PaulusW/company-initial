package io.rscale.training.company;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.UnsatisfiedDependencyException;
import org.springframework.cloud.config.java.AbstractCloudConfig;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;

import javax.sql.DataSource;
import java.sql.Connection;
import java.sql.SQLException;

@Profile("cloud")
@Configuration
public class DataSourceConfig extends AbstractCloudConfig {

    private static final Logger logger = LoggerFactory.getLogger(DataSourceConfig.class);

    public DataSourceConfig() {
        logger.info(this.getClass() + " loaded");
    }

    @Bean
    public DataSource dataSource() throws Exception {
        DataSource dataSource = cloud().getSingletonServiceConnector(DataSource.class, null);
        if ( !isMySQL(dataSource.getConnection())) {
            logger.error("MySQL required when running cloud profile.");
            throw new UnsatisfiedDependencyException("javax.sql.DataSource", "javax.sql.DataSource", "javax.sql.DataSource", "MySQL required when running cloud profile.");
        }
        return dataSource;
    }

    private boolean isMySQL(Connection connection) throws SQLException {
        logger.info("Checking connection driver name : " + connection.getMetaData().getDriverName());
        return connection.getMetaData().getDriverName().toLowerCase().contains("mysql");
    }

}
