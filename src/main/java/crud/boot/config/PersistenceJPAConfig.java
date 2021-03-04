package crud.boot.config;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;
import org.springframework.core.env.Environment;
import org.springframework.dao.annotation.PersistenceExceptionTranslationPostProcessor;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.jdbc.datasource.DriverManagerDataSource;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.JpaVendorAdapter;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import javax.persistence.PersistenceContext;
import javax.persistence.PersistenceContextType;
import java.util.Arrays;
import java.util.Map;
import java.util.Objects;
import java.util.Properties;
import java.util.stream.Collectors;

@Configuration
@EnableJpaRepositories(basePackages = {"crud.boot.repos"})
@PropertySource("classpath:application.properties")
@EnableTransactionManagement
public class PersistenceJPAConfig {

    private Environment env;
    @Autowired
    public PersistenceJPAConfig(Environment env) {
        this.env = env;
    }

    @Bean(name="entityManagerFactory")
    public LocalContainerEntityManagerFactoryBean entityManagerFactory() {
        LocalContainerEntityManagerFactoryBean em
                = new LocalContainerEntityManagerFactoryBean();
        em.setDataSource(dataSource());
        em.setPackagesToScan("crud.boot.model");
        JpaVendorAdapter vendorAdapter = new HibernateJpaVendorAdapter();
        em.setJpaVendorAdapter(vendorAdapter);
        em.setJpaPropertyMap(hibernateProperties());
        return em;
    }

    @Bean(name="dataSource")
    public DriverManagerDataSource dataSource() {
        DriverManagerDataSource dataSource = new DriverManagerDataSource();
        dataSource.setDriverClassName(env.getRequiredProperty("db.driver"));
        dataSource.setUrl(env.getProperty("db.url"));
        dataSource.setUsername(env.getProperty("db.username"));
        dataSource.setPassword(env.getProperty("db.password"));
        return dataSource;
    }

    /*
        @Bean
        public LocalSessionFactoryBean getSessionFactory() {
            LocalSessionFactoryBean factoryBean = new LocalSessionFactoryBean();
            factoryBean.setDataSource(getDataSource());

            factoryBean.setHibernateProperties(hibernateProperties());
            factoryBean.setPackagesToScan(new String[]{"crud.model"});
            return factoryBean;
        }
    */
    final Map<String,Object> hibernateProperties() {
        return Arrays.stream(new String[]{"hibernate.show_sql",
                "hibernate.hbm2ddl.auto","hibernate.dialect","hibernate.format_sql",
                "hibernate.use_sql_comments"})
                .collect(Collectors.toMap(s -> s, s->env.getRequiredProperty(s)));
    }

    @Bean(name="transactionManager")
    public PlatformTransactionManager transactionManager() {
        JpaTransactionManager transactionManager = new JpaTransactionManager();
        transactionManager.setEntityManagerFactory(entityManagerFactory().getObject());
        transactionManager.setDataSource(dataSource());
        return transactionManager;
    }

    @Bean
    public PersistenceExceptionTranslationPostProcessor exceptionTranslation() {
        return new PersistenceExceptionTranslationPostProcessor();
    }

}

