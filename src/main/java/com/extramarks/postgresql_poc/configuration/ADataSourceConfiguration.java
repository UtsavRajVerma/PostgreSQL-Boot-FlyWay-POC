package com.extramarks.postgresql_poc.configuration;

import com.extramarks.postgresql_poc.entity.a.SourceUser;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
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
        "com.extramarks.postgresql_poc.entity.a",
        entityManagerFactoryRef = "aEntityManagerFactory",
        transactionManagerRef = "aTransactionManager"
)
public class ADataSourceConfiguration {
    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource.a")
    public DataSourceProperties aDataSourceProperties() {
        return new DataSourceProperties();
    }

    @Primary
    @Bean
    @ConfigurationProperties("spring.datasource.a.configuration")
    public DataSource aDataSource() {
        return aDataSourceProperties().initializeDataSourceBuilder()
                .type(HikariDataSource.class).build();
    }

    @Primary
    @Bean(name = "aEntityManagerFactory")
    public LocalContainerEntityManagerFactoryBean aEntityManagerFactory(EntityManagerFactoryBuilder builder) {
        Map<String,Object> map=new HashMap<>();
        map.put("hibernate.dialect","org.hibernate.dialect.MySQL5InnoDBDialect");
        LocalContainerEntityManagerFactoryBean build = builder
                .dataSource(aDataSource())
                .packages(SourceUser.class)
                .build();
        build.setJpaPropertyMap(map);
        return build;
    }

    @Primary
    @Bean
    public PlatformTransactionManager aTransactionManager(
            final @Qualifier("aEntityManagerFactory") LocalContainerEntityManagerFactoryBean aEntityManagerFactory) {
        return new JpaTransactionManager(aEntityManagerFactory.getObject());
    }
}
