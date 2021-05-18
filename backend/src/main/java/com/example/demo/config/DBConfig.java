package com.example.demo.config;

import com.zaxxer.hikari.HikariDataSource;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.boot.orm.jpa.EntityManagerFactoryBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.orm.hibernate5.LocalSessionFactoryBean;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;

import javax.persistence.EntityManager;
import javax.sql.DataSource;

@Configuration
@RequiredArgsConstructor
@EnableJpaRepositories(
        entityManagerFactoryRef = "entityManager",
        transactionManagerRef = "transactionManager",
        basePackages = {"com.example.demo.repository"}
)
public class DBConfig {


    @Bean("dataSource1")
    @Primary
    @ConfigurationProperties("core.datasource")
    public DataSource tqCoreDataSource() {
        HikariDataSource hikariDataSource = new HikariDataSource();
        return hikariDataSource;
    }
    @Bean(name="entityManager")
    public LocalContainerEntityManagerFactoryBean entityManager(EntityManagerFactoryBuilder builder, @Qualifier("dataSource1")DataSource dataSource) {
        return builder.dataSource(dataSource)
                .packages("com.example.demo.entity")
                .persistenceUnit("mydb")
                .build();
    }
}
