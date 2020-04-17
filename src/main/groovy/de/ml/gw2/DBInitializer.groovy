package de.ml.gw2

import groovy.sql.Sql

import java.sql.SQLException

class DBInitializer {

    public static final Sql sql = connection

    private static Sql getConnection() throws URISyntaxException, SQLException {
        if (System.getenv("DATABASE_URL") == null) {
            return Sql.newInstance("jdbc:hsqldb:mem:testdb",  "org.hsqldb.jdbc.JDBCDriver")
        }
        URI dbUri = new URI(System.getenv("DATABASE_URL"));

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];
        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";
        String driver = "org.postgresql.Driver"

        return Sql.newInstance(dbUrl, username, password, driver)
    }
}

