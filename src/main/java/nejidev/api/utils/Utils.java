package nejidev.api.utils;

import nejidev.main.MainApplication;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Guild;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;

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

    public static InputStream convertImage(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos );
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();
       return new ByteArrayInputStream(imageInByte);
    }

    public static Image openImage(String url) throws IOException {
        URL urll = new URL(url);
        URLConnection connection = urll.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        return ImageIO.read(connection.getInputStream());

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
