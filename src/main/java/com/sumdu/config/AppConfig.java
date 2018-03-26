package com.sumdu.config;

import com.sumdu.dao.DoubleClassDao;
import com.sumdu.dao.IDoubleClassDao;
import com.sumdu.entity.DoubleClass;
import org.apache.commons.dbcp.BasicDataSource;
import org.apache.log4j.Logger;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.env.Environment;
import org.springframework.orm.hibernate4.HibernateTemplate;
import org.springframework.orm.hibernate4.HibernateTransactionManager;
import org.springframework.orm.hibernate4.LocalSessionFactoryBuilder;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

@Configuration
@EnableTransactionManagement
public class AppConfig {

    private static Properties props = new Properties();
    private static Logger LOG = Logger.getLogger(AppConfig.class);

    @Autowired
    private Environment env;

    @Bean
    public IDoubleClassDao doubleClassDao() {
        return new DoubleClassDao();
    }

    @Bean
    public HibernateTemplate hibernateTemplate() {
        HibernateTemplate hb = new HibernateTemplate();
        hb.setCheckWriteOperations(false);
        hb.setSessionFactory(sessionFactory());
        return hb;
    }

    @Bean
    public SessionFactory sessionFactory() {
        LocalSessionFactoryBuilder localSessionFactoryBuilder = new LocalSessionFactoryBuilder(getDataSource());
        localSessionFactoryBuilder.addProperties(hibernateProperties());
        localSessionFactoryBuilder.addAnnotatedClasses(DoubleClass.class);
        return localSessionFactoryBuilder.buildSessionFactory();
    }

    @Bean
    public DataSource getDataSource() {
        BasicDataSource dataSource = new BasicDataSource();
        dataSource.setDriverClassName(props.getProperty("db.driver"));
        dataSource.setUrl(props.getProperty("db.url"));
        dataSource.setUsername(props.getProperty("db.username"));
        dataSource.setPassword(props.getProperty("db.password"));
        return dataSource;
    }

    @Bean
    public HibernateTransactionManager hibTransMan() {
        return new HibernateTransactionManager(sessionFactory());
    }

    final Properties hibernateProperties() {
        final Properties hibernateProperties = new Properties();
        hibernateProperties.setProperty("hibernate.hbm2ddl.auto", props.getProperty("hibernate.hbm2ddl.auto"));
        hibernateProperties.setProperty("hibernate.dialect", props.getProperty("hibernate.dialect"));
        return hibernateProperties;
    }

    static {
        FileInputStream in;
        try {
            in = new FileInputStream("src\\main\\resources\\application.properties");
            props.load(in);
            in.close();
        } catch (IOException e) {
            LOG.error(e);
        }
    }

    public static Properties getProperties() {
        return props;
    }
}  
 