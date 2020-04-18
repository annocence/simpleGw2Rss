package de.ml.gw2.db

import groovy.sql.Sql

import java.sql.SQLException

class DBAccessor {
    static Sql sql = initTable()

    static String getDailyMessageOr(String defaultMessage) {
        try {
            def rows = sql.rows('''SELECT daily_message FROM gw2rss;''', {})
            return rows.size() > 0 ? rows[0].get("daily_message") : defaultMessage
        } catch (SQLException | IOException e) {
            println "Error occurred during DB connection: ${e}"
            return defaultMessage
        }
    }

    private static Sql initTable() {
        Sql sql = DBInitializer.sql
        DBInitializer.sql.execute('''CREATE TABLE IF NOT EXISTS gw2rss(
            id integer not null,
            daily_message varchar(50)       
        );''')
        return sql
    }

    static void setDailyMessage(String newMessage) {
        def rows = sql.rows('''SELECT playgw2 FROM gw2rss;''', {})
        if (rows.size() == 0) {
            sql.execute("INSERT INTO gw2rss VALUES (0, '$newMessage' );")
        } else {
            sql.execute("UPDATE gw2rss SET daily_message = '$newMessage' WHERE id = 0;")
        }
    }
}
