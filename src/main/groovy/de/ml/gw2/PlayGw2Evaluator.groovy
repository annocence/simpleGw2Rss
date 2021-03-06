package de.ml.gw2

import com.google.inject.Singleton
import de.ml.gw2.db.DBAccessor
import groovy.transform.CompileStatic

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
        def answer = DAILYANSWER // (now.getDayOfWeek().value < 6) ? workdays[hour] : weekend[hour]
        if (count > 10) {
            return "Come back later!"  //+ " (Asking (lasteTime=$lastTimeAsked) for the ${count}. time today $now)"
        }
        return answer.replace(DAILYANSWER, getDailyMessage())
        //+ " (Asking (lasteTime=$lastTimeAsked) for the ${count}. time today $now)"
    }

    void setDailyMessage(String newMessage) {
        this.dailyMessage = newMessage
        DBAccessor.setDailyMessage(newMessage)
    }

    String getDailyMessage() {
        return DBAccessor.getDailyMessageOr(this.dailyMessage)
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
