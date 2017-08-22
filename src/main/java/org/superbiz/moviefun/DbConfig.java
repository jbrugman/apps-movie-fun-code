package org.superbiz.moviefun;

import com.mysql.jdbc.jdbc2.optional.MysqlDataSource;
import com.zaxxer.hikari.HikariConfig;
import com.zaxxer.hikari.HikariDataSource;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.autoconfigure.jdbc.DataSourceBuilder;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.orm.jpa.JpaTransactionManager;
import org.springframework.orm.jpa.LocalContainerEntityManagerFactoryBean;
import org.springframework.orm.jpa.vendor.Database;
import org.springframework.orm.jpa.vendor.HibernateJpaVendorAdapter;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.transaction.PlatformTransactionManager;

import javax.sql.DataSource;

@Configuration
public class DbConfig {

    @Value("${moviefun.datasources.albums.url}") String album_Url;
    @Value("${moviefun.datasources.albums.username}") String album_Username;
    @Value("${moviefun.datasources.albums.password}") String album_Password;
    @Value("${moviefun.datasources.movies.url}") String movie_Url;
    @Value("${moviefun.datasources.movies.username}") String movie_Username;
    @Value("${moviefun.datasources.movies.password}") String movie_Password;

    private LocalContainerEntityManagerFactoryBean createFactoryBean(
            String unitName,
            DataSource dataSource,
            HibernateJpaVendorAdapter hibernateJpaVendorAdapter
    ){
        LocalContainerEntityManagerFactoryBean factoryBean = new LocalContainerEntityManagerFactoryBean();
        factoryBean.setDataSource(dataSource);
        factoryBean.setJpaVendorAdapter(hibernateJpaVendorAdapter);
        factoryBean.setPackagesToScan(DbConfig.class.getPackage().getName());
        factoryBean.setPersistenceUnitName(unitName);
        return factoryBean;
    }

    private static DataSource createConnectionPool(String url, String username, String password) {
        MysqlDataSource dataSource = new MysqlDataSource();
        dataSource.setURL(url);
        dataSource.setUser(username);
        dataSource.setPassword(password);
        HikariConfig config = new HikariConfig();
        config.setDataSource(dataSource);
        return new HikariDataSource(config);
    }

    @Bean
    HibernateJpaVendorAdapter hibernateJpaVendorAdapter() {
        HibernateJpaVendorAdapter hibernateJpaVendorAdapter = new HibernateJpaVendorAdapter();
        hibernateJpaVendorAdapter.setDatabase(Database.MYSQL);
        hibernateJpaVendorAdapter.setDatabasePlatform("org.hibernate.dialect.MySQL5InnoDBDialect");
        hibernateJpaVendorAdapter.setGenerateDdl(true);
        return hibernateJpaVendorAdapter;
    }

    @Bean
    public DataSource albumsDataSource() {
        return createConnectionPool(
                album_Url,album_Username,album_Password
        );
    }

    @Bean
    @ConfigurationProperties("moviefun.datasources.movies")
    public DataSource moviesDataSource() {
        return createConnectionPool(
                movie_Url,movie_Username,movie_Password
        );
    }

    @Bean
    @Qualifier("albumsEntityManagerFactoryBean")
    LocalContainerEntityManagerFactoryBean albumsEntityManagerFactoryBean(
            @Qualifier("albumsDataSource") DataSource dataSource,
            HibernateJpaVendorAdapter hibernateJpaVendorAdapter) {
        return createFactoryBean("albums",dataSource,hibernateJpaVendorAdapter);
    }

    @Bean
    @Qualifier("moviesEntityManagerFactoryBean")
    LocalContainerEntityManagerFactoryBean moviesEntityManagerFactoryBean(
            @Qualifier("moviesDataSource") DataSource dataSource,
            HibernateJpaVendorAdapter hibernateJpaVendorAdapter) {
        return createFactoryBean("movies",dataSource,hibernateJpaVendorAdapter);
    }

    @Bean
    PlatformTransactionManager albumsTransactionManager(@Qualifier("albumsEntityManagerFactoryBean") LocalContainerEntityManagerFactoryBean factoryBean) {
        return new JpaTransactionManager(factoryBean.getObject());
    }
    @Bean
    PlatformTransactionManager moviesTransactionManager(@Qualifier("moviesEntityManagerFactoryBean") LocalContainerEntityManagerFactoryBean factoryBean) {
        return new JpaTransactionManager(factoryBean.getObject());
    }
}


