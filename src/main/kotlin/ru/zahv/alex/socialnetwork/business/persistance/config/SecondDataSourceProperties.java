package ru.zahv.alex.socialnetwork.business.persistance.config;

import org.springframework.boot.autoconfigure.jdbc.DataSourceProperties;

import javax.sql.DataSource;

//@Component("secondDbDs")
//@ConfigurationProperties(prefix = "spring.second-db")
//@Getter
//@Setter
public class SecondDataSourceProperties extends DataSourceProperties {
    private ClassLoader classLoader;

    /**
     * Whether to generate a random datasource name.
     */
    private boolean generateUniqueName = true;

    /**
     * Datasource name to use if "generate-unique-name" is false. Defaults to "testdb"
     * when using an embedded database, otherwise null.
     */
    private String name;

    /**
     * Fully qualified name of the connection pool implementation to use. By default, it
     * is auto-detected from the classpath.
     */
    private Class<? extends DataSource> type;

    /**
     * Fully qualified name of the JDBC driver. Auto-detected based on the URL by default.
     */
    private String driverClassName;

    /**
     * JDBC URL of the database.
     */
    private String url;

    /**
     * Login username of the database.
     */
    private String username;

    /**
     * Login password of the database.
     */
    private String password;
}
