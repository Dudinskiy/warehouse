package org.example.warehouse.config;

import lombok.SneakyThrows;
import org.example.warehouse.dao.CountriesDAOPostgreImpl;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.jdbc.datasource.SingleConnectionDataSource;
import org.springframework.jndi.JndiTemplate;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.TransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.TransactionManagementConfigurer;

import javax.naming.Context;
import javax.naming.InitialContext;
import javax.naming.NamingException;
import javax.sql.DataSource;
import java.util.Hashtable;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
@PropertySource("classpath:application.properties")
public class DataSourceConfig implements TransactionManagementConfigurer {
    @Value("${spring.datasource.url}")
    private String url;
    @Value("${spring.datasource.username}")
    private String userName;
    @Value("${spring.datasource.password}")
    private String password;
    @Value("${spring.datasource.driver-class-name}")
    private String driver;

    private static final Logger LOGGER = LoggerFactory.getLogger(DataSourceConfig.class);

    @Bean
    public DataSource dataSource() {
//        SingleConnectionDataSource dataSource = new SingleConnectionDataSource();
//
//        dataSource.setSuppressClose(true);
//        dataSource.setUrl(url);
//        dataSource.setUsername(userName);
//        dataSource.setPassword(password);
//        dataSource.setDriverClassName(driver);
//
//        return dataSource;
        DataSource dataSource = null;
        Context context = null;
        Hashtable hashtable = new Hashtable();
        hashtable.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
        hashtable.put(Context.PROVIDER_URL, "t3://localhost:7001");

        try {
            context = new InitialContext(hashtable);
            dataSource = (DataSource) context.lookup("jdbc/WarehouseDataSource");
        } catch (NamingException e) {
            LOGGER.error("Error DataSource setup", e);
        } finally {
            try {
                if (context != null) {
                    context.close();
                }
            } catch (NamingException e) {
                LOGGER.error("Error DataSource setup", e);
            }
        }
//        Properties properties = new Properties();
//        properties.put(Context.INITIAL_CONTEXT_FACTORY, "weblogic.jndi.WLInitialContextFactory");
//        properties.put(Context.PROVIDER_URL, "t3://localhost:7001");
//
//        JndiTemplate jndiTemplate = new JndiTemplate(properties);
//        try {
//            dataSource = (DataSource) jndiTemplate.lookup("java:comp/env/JDBC_DataSource_Warehouse");
//        } catch (NamingException e) {
//
//        }
        return dataSource;
    }

    @Bean
    public PlatformTransactionManager txManager() {
        return new DataSourceTransactionManager(dataSource());
    }


    @Override
    public TransactionManager annotationDrivenTransactionManager() {
        return txManager();
    }

//    public DataSource getDataSource(){
//        DriverManagerDataSource dataSource = new DriverManagerDataSource();
//        dataSource.setDriverClassName(driver);
//        dataSource.setUrl(url);
//        dataSource.setUsername(userName);
//        dataSource.setPassword(password);
//
//        return dataSource;
//    }


}
