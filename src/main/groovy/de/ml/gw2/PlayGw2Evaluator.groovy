package de.ml.gw2

import com.google.inject.Singleton
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
        def now = LocalDateTime.now(ZoneId.of(ZoneId.SHORT_IDS["PST"]))
//        now = LocalDateTime.parse('2007-12-03T22:22:30')
        lastTimeAsked = (lastTimeAsked != null && lastTimeAsked.plusHours(24).isBefore(now)) ? lastTimeAsked : now
        lastTimeAsked.getDayOfYear() != now.dayOfYear ? count = 0 : count++

        def hour = now.getHour()
        def answer = (now.getDayOfWeek().value < 6) ? workdays[hour] : weekend[hour]
        return answer.replace(DAILYANSWER, dailyMessage) // + " (Asking for the ${count}. time today)"
    }

    void setDailyMessage(String newMessage) {
        this.dailyMessage = newMessage
    }

    private static Map<Integer, String> getWorkdaysMap() {
        final Map<Integer, String> map = new HashMap<>()
        (0..6).forEach { Integer i -> map.put(i, "go sleep") }
        (7..9).forEach { Integer i -> map.put(i, "you are really bored, aren't you?") }
        (10..17).forEach { Integer i -> map.put(i, "shouldn't you work?") }
        (18..23).forEach { Integer i -> map.put(i, DAILYANSWER) }
        return map
    }

    private static Map<Integer, String> getWeekendsMap() {
        final Map<Integer, String> map = new HashMap<>()
        (0..6).forEach { Integer i -> map.put(i, "go sleep") }
        (7..9).forEach { Integer i -> map.put(i, "you are really bored, aren't you?") }
        (10..17).forEach { Integer i -> map.put(i, "shouldn't spend time with your family?") }
        (18..23).forEach { Integer i -> map.put(i, DAILYANSWER) }
        return map
    }
}
