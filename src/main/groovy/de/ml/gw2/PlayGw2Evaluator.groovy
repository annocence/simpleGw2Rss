package de.ml.gw2

import com.google.inject.Singleton
import groovy.sql.GroovyRowResult
import groovy.sql.Sql
import groovy.transform.CompileStatic
import org.postgresql.util.PSQLException

import java.sql.SQLException
import java.time.LocalDateTime
import java.time.ZoneId

@CompileStatic
@Singleton
class PlayGw2Evaluator {
    public static final String DAILYANSWER = "DAILYANSWER"
    private final Map<Integer, String> workdays = getWorkdaysMap()
    private final Map<Integer, String> weekend = getWeekendsMap()

    private String dailyMessage = "Not Today"
    private int count
    private LocalDateTime lastTimeAsked

    String evaluatePlayGw2() {
        def now = LocalDateTime.now(ZoneId.of(ZoneId.SHORT_IDS["ECT"]))
        lastTimeAsked = (lastTimeAsked != null && lastTimeAsked.plusHours(1).isAfter(now)) ? lastTimeAsked : now
        lastTimeAsked.getHour() != now.hour ? count = 0 : count++

        def hour = now.getHour()
        def answer = (now.getDayOfWeek().value < 6) ? workdays[hour] : weekend[hour]
        if (count > 10) {
            return "Come back later!"  //+ " (Asking (lasteTime=$lastTimeAsked) for the ${count}. time today $now)"
        }
        return answer.replace(DAILYANSWER, getDailyMessage())
        //+ " (Asking (lasteTime=$lastTimeAsked) for the ${count}. time today $now)"
    }

    void setDailyMessage(String newMessage) {
        this.dailyMessage = newMessage
    }

    String getDailyMessage() {
        try {
            def sql = DBInitializer.sql
            sql.execute('''CREATE TABLE IF NOT EXISTS gw2rss(
        id integer not null,
        playgw2 varchar(50)       
        );''')
            def rows = sql.rows('''SELECT playgw2 FROM gw2rss;''', {})
            return rows.size() > 0 ? rows[0] : this.dailyMessage
        } catch (PSQLException| SQLException | IOException e) {
            println "Error occurred during DB connection: ${e}"
            return this.dailyMessage
        }
    }

    private static Map<Integer, String> getWorkdaysMap() {
        final Map<Integer, String> map = new HashMap<>()
        (0..6).forEach { Integer i -> map.put(i, "go sleep!") }
        (7..9).forEach { Integer i -> map.put(i, "you are really bored, aren't you?") }
        (10..17).forEach { Integer i -> map.put(i, "shouldn't you work?") }
        (18..23).forEach { Integer i -> map.put(i, DAILYANSWER) }
        return map
    }

    private static Map<Integer, String> getWeekendsMap() {
        final Map<Integer, String> map = new HashMap<>()
        (0..6).forEach { Integer i -> map.put(i, "go sleep!") }
        (7..9).forEach { Integer i -> map.put(i, "you are really bored, aren't you?") }
        (10..17).forEach { Integer i -> map.put(i, "shouldn't you spend time with your family?") }
        (18..23).forEach { Integer i -> map.put(i, DAILYANSWER) }
        return map
    }
}
