package ru.zahv.alex.socialnetwork.business.persistance.config

import com.zaxxer.hikari.HikariDataSource
import liquibase.integration.spring.SpringLiquibase
import org.springframework.beans.factory.annotation.Qualifier
import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties
import org.springframework.boot.autoconfigure.liquibase.LiquibaseProperties
import org.springframework.boot.context.properties.ConfigurationProperties
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate
import javax.sql.DataSource

@Configuration
class DaoConfig {

    @Bean(name = ["masterDB"])
    @ConfigurationProperties(prefix = "spring.datasource")
    fun masterDataSource(): DataSource {
        return masterDbProperties().initializeDataSourceBuilder().type(HikariDataSource::class.java).build()
    }

    @Bean(name = ["masterTemplate"])
    fun masterTemplate(@Qualifier("masterDB") ds: DataSource): NamedParameterJdbcTemplate {
        return NamedParameterJdbcTemplate(ds)
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.datasource")
    fun masterDbProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.second-db")
    fun slaveDbProperties(): DataSourceProperties {
        return DataSourceProperties()
    }

    @Bean(name = ["slaveDB"])
    @ConfigurationProperties(prefix = "spring.second-db")
    fun slaveDataSource(): DataSource {
        return slaveDbProperties().initializeDataSourceBuilder().type(HikariDataSource::class.java).build()
    }

    @Bean(name = ["slaveTemplate"])
    fun slaveTemplate(@Qualifier("slaveDB") ds: DataSource): NamedParameterJdbcTemplate {
        return NamedParameterJdbcTemplate(ds)
    }

    @Bean
    @ConfigurationProperties(prefix = "spring.liquibase")
    fun primaryLiquibaseProperties(): LiquibaseProperties {
        return LiquibaseProperties()
    }

    @Bean
    fun primaryLiquibase(@Qualifier("masterDB") masterDataSource: DataSource): SpringLiquibase? {
        return springLiquibase(masterDataSource, primaryLiquibaseProperties())
    }

    private fun springLiquibase(dataSource: DataSource, properties: LiquibaseProperties): SpringLiquibase? {
        val liquibase = SpringLiquibase()
        liquibase.dataSource = dataSource
        liquibase.changeLog = properties.changeLog
        liquibase.contexts = properties.contexts
        liquibase.defaultSchema = properties.defaultSchema
        liquibase.isDropFirst = properties.isDropFirst
        liquibase.setShouldRun(properties.isEnabled)
        return liquibase
    }
}
