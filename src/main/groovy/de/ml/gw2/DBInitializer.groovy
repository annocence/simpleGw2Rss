package de.ml.gw2

import groovy.sql.Sql

import java.sql.SQLException

class DBInitializer {

    public static final Sql sql = connection

    private static Sql getConnection() throws URISyntaxException, SQLException {
        URI dbUri = new URI(System.getenv("DATABASE_URL")?:"postgres://a:b@localhost:5432/testdb");

        String username = dbUri.getUserInfo().split(":")[0];
        String password = dbUri.getUserInfo().split(":")[1];

        String dbUrl = "jdbc:postgresql://" + dbUri.getHost() + ':' + dbUri.getPort() + dbUri.getPath() + "?sslmode=require";
        return Sql.newInstance(dbUrl, username, password, 'org.postgresql.Driver')
    }
}

