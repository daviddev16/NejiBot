package nejidev.api.utils;

import nejidev.api.Bot;
import net.dv8tion.jda.api.entities.Emote;
import org.jetbrains.annotations.NotNull;

public class GuildUtils {

    public static @NotNull Emote findEmote(@NotNull String name, @NotNull Bot bot) {
        for(Emote emotes : bot.getOfficialGuild().getEmotes()){
            if(emotes.getName().equalsIgnoreCase(name)) return emotes;
        }
        throw new NullPointerException("Could not found emote \"" + name + "\".");
    }
}
