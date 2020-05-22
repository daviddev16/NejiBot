package nejidev.api.utils;

import java.time.Duration;
import java.util.Timer;
import java.util.TimerTask;

/*programar delay para executar alguma ação*/
@SuppressWarnings("ALL")
public class Schedule {

    public static synchronized void newScheduleEvent(Duration duration, Runnable runnable){
        new Schedule(duration, runnable);
    }

    private Schedule(Duration duration, Runnable runnable){
        Timer timer = new Timer();
        timer.schedule(new TimerTask() {
            public void run() {
                try {
                    runnable.run();
                }catch (NullPointerException ignored){}
            }
        }, duration.toMillis());

    }

}
