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

    /*acessar InputStream de dentro das Resources*/
    public static @Nullable InputStream getInputStream(@NotNull String path) throws RuntimeException {
        @Nullable InputStream result = null;
        try {
            result = Utils.class.getResourceAsStream(path);
        } catch (NullPointerException e) {
            e.addSuppressed(new NullPointerException("Could not found the InputStream :/"));
        }
        return result;
    }

    /*
    * Converter um BufferedImage em um InputStream, necessario para enviar
    * imagens pela JDA
    * */
    public static InputStream convertImage(BufferedImage image) throws IOException {
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        ImageIO.write(image, "png", baos );
        baos.flush();
        byte[] imageInByte = baos.toByteArray();
        baos.close();
       return new ByteArrayInputStream(imageInByte);
    }

    /**
     * transformar imagem da url em uma Imagem em java.
     */
    public static Image openImage(String url) throws IOException {
        URL urll = new URL(url);
        URLConnection connection = urll.openConnection();
        connection.setRequestProperty("User-Agent", "Mozilla/5.0");
        return ImageIO.read(connection.getInputStream());

    }

    /*remover indice de uma array*/
    public static String[] removeIndex(String[] arr,  int index) {
        if (arr == null || index < 0 || index >= arr.length) {
            return arr;
        }
        String[] anotherArray = new String[arr.length - 1];
        for (int i = 0, k = 0; i < arr.length; i++) {
            if (i == index) {
                continue;
            }
            anotherArray[k++] = arr[i];
        }
        return anotherArray;
    }

    /*Metodo substituido em NejiAPI*/
    @Deprecated
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
