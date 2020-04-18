package de.ml.gw2.db

import groovy.sql.Sql

import java.sql.SQLException

class DBAccessor {

    static String getDailyMessageOr(String defaultMessage) {
        try {
            def rows = DBInitializer.sql.rows('''SELECT daily_message FROM gw2rss;''', {})
            return rows.size() > 0 ? rows[0].get("daily_message") : defaultMessage
        } catch (SQLException | IOException e) {
            println "Error occurred during DB connection: ${e}"
            return defaultMessage
        }
    }

    static void setDailyMessage(String newMessage) {
        def rows = DBInitializer.sql.rows('''SELECT daily_message FROM gw2rss;''')
        if (rows.size() == 0) {
            DBInitializer.sql.execute("INSERT INTO gw2rss VALUES (0, $newMessage );")
        } else {
            DBInitializer.sql.execute("UPDATE gw2rss SET daily_message = $newMessage WHERE id = 0;")
        }
    }
}
