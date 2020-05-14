package nejidev.utils;

import nejidev.api.NejiAPI;
import nejidev.api.utils.ImageUtils;
import nejidev.api.utils.Utils;
import net.dv8tion.jda.api.entities.User;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class ImageCreator {

    /*criar imagem de login quando alguem entrar no servidor*/
    public static InputStream createWelcome(User user) throws IOException {

        int gap = 10;
        int WIDTH = 512, HEIGHT = 128 + (gap * 2);

        BufferedImage background = new BufferedImage(WIDTH,HEIGHT,BufferedImage.TYPE_INT_ARGB);

        /*imagem do usuario*/
        BufferedImage userImage = ImageUtils.createBufferedImage(Utils.openImage(user.getAvatarUrl()));

        Graphics2D graphics = background.createGraphics();

        /*background com gradient*/
        Color dominantColor = Color.decode(ImageUtils.getDominantColor(userImage)).brighter();
        graphics.setPaint(new GradientPaint(0, 0, Color.white, 400, 400, dominantColor));
        graphics.fillRoundRect(0, 0, WIDTH, HEIGHT, 2, 2);

        /*textos*/
        graphics.setColor(dominantColor.darker().darker());
        graphics.setFont(new Font("Segoe UI", Font.BOLD, 32));
        graphics.drawString("BEM VINDO", gap, gap + 32);

        /*username*/
        graphics.setFont(new Font("Segoe UI", Font.BOLD, 22));
        graphics.drawString(user.getName() + " !", gap, gap + 32+23);

        /*simbolo happy*/
        BufferedImage happy = ImageUtils.createBufferedImage(Utils.openImage(NejiAPI.happy().getImageUrl()).getScaledInstance(32, 54, Image.SCALE_SMOOTH));
        graphics.drawImage(happy, 2 + gap,(128-32) - gap, null);

        /*mostrar avatar*/
        graphics.drawImage(userImage, (512 - (128+gap)), gap, null);
        graphics.dispose();
        return Utils.convertImage(background);
    }

}
