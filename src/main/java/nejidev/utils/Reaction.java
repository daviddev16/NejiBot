package nejidev.utils;

import nejidev.api.Bot;
import nejidev.api.utils.Utils;
import nejidev.main.MainApplication;
import net.dv8tion.jda.api.entities.Emote;

public class Reaction {

    public static Emote ok(){
        return Utils.findEmoteByName("ok");
    }

    public static Emote warning(){
        return Utils.findEmoteByName("warning");
    }

    public static Emote denied(){
        return Utils.findEmoteByName("denied");
    }

}
