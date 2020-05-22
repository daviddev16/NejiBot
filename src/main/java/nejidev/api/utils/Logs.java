package nejidev.api.utils;

import nejidev.api.NejiAPI;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

import java.time.LocalDateTime;

public final class Logs {

    public static final long LOGS_TEXTCHANNEL_ID = 713239841957085205L;

    public static MessageAction send(String message) {
        return NejiAPI.getServerGuild().getTextChannelById(LOGS_TEXTCHANNEL_ID).sendMessage("```"+ logPrefix() + message + "```");
    }

    private static String logPrefix(){
        LocalDateTime ldt = LocalDateTime.now();
        return "[" + ldt.getDayOfMonth() + "/" + ldt.getMonth().getValue() + "/" + ldt.getYear() + " - " + ldt.getHour() + ":" + ldt.getMinute() + ":" + ldt.getSecond() + "]: ";
    }

}
