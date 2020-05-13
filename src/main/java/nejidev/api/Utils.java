package nejidev.api;

import nejidev.main.MainApplication;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Guild;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.io.InputStream;

public class Utils {

    public static @Nullable InputStream getInputStream(@NotNull String path) throws RuntimeException {
        @Nullable InputStream result = null;
        try {
            result = Utils.class.getResourceAsStream(path);
        } catch (NullPointerException e) {
            e.addSuppressed(new NullPointerException("Could not found the InputStream :/"));
        }
        return result;
    }

    public static Emote findEmoteByName(String name){
        Guild guild = MainApplication.getBot().getOfficialGuild();
        for(Emote emotes : guild.getEmotes()){
            if(emotes.getName().equalsIgnoreCase(name)){
                return emotes;
            }
        }
        throw new NullPointerException("Could not found the Emote \"" + name + "\"");
    }

}
