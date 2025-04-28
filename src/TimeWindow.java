import java.time.LocalTime;

public class TimeWindow {
    LocalTime start;
    LocalTime end;

    public TimeWindow(LocalTime s, LocalTime e){
        start=s;
        end=e;
    }

    public boolean isWithin(LocalTime currentTime) {
        if (start.isBefore(end)) {
            return !currentTime.isBefore(start) && !currentTime.isAfter(end);
        } else {
            return !currentTime.isBefore(start) || !currentTime.isAfter(end);
        }
    }

}
