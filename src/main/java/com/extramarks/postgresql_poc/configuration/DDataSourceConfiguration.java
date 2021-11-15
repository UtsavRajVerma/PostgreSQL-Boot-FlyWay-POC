package com.extramarks.postgresql_poc.configuration;

import com.extramarks.postgresql_poc.entity.d.TargetUOD;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import javax.sql.DataSource;
import java.util.HashMap;
import java.util.Map;

@Configuration
@EnableTransactionManagement
@EnableJpaRepositories(basePackages =
        "com.extramarks.postgresql_poc.entity.d",
        entityManagerFactoryRef = "dEntityManagerFactory",
        transactionManagerRef = "dTransactionManager"
)

public class DDataSourceConfiguration {
    @Bean
    @ConfigurationProperties("spring.datasource.d")
    public DataSourceProperties dDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Bean
    @ConfigurationProperties("spring.datasource.d.configuration")
    public DataSource dDataSource() {
        return dDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Bean(name = "dEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean dEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        Map<String,Object> map=new HashMap<>();
        map.put("hibernate.dialect","org.hibernate.dialect.PostgreSQL94Dialect");
        LocalContainerEntityManagerFactoryBean build = builder
                .dataSource(dDataSource())
                .packages(TargetUOD.class)
                .build();
        build.setJpaPropertyMap(map);
        return build;
    }

    @Bean
    public PlatformTransactionManager dTransactionManager(
            final @Qualifier("dEntityManagerFactory") LocalContainerEntityManagerFactoryBean dEntityManagerFactory) {
        return new JpaTransactionManager(dEntityManagerFactory.getObject());
    }
}