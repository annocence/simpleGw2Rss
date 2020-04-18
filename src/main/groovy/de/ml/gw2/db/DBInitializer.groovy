package de.ml.gw2.db

import groovy.sql.Sql

import java.sql.SQLException

class DBInitializer {

    public static final Sql sql = connection

    private static Sql getConnection() throws URISyntaxException, SQLException {
        if (System.getenv("DATABASE_URL") == null) {
            def instance = Sql.newInstance("jdbc:hsqldb:mem:testdb", "org.hsqldb.jdbc.JDBCDriver")
            initDB(instance)
            return instance
        }
        URI dbUri = new URI(System.getenv("DATABASE_URL"));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";
        String driver = "org.postgresql.Driver"

        def instance = Sql.newInstance(dbUrl, username, password, driver)
        initDB(instance)
        return instance
    }

    private static void initDB(Sql instance) {
        instance.execute('''CREATE TABLE IF NOT EXISTS gw2rss (
            id integer not null,
            daily_message varchar(50)       
        );''')
    }
}

