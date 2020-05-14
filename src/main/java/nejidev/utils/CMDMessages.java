package nejidev.utils;

import nejidev.api.commands.ReceivedInfo;
import nejidev.main.MainApplication;
import nejidev.main.NejiBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;

import java.awt.*;

public class CMDMessages {

    /*DCM = Display Command Message*/

    public static EmbedBuilder DCM(ReceivedInfo info, String title, String hexadecimalColor, String msg){
        return new EmbedBuilder().setAuthor("Resultados para " + info.getSender().getUser().getName())
                .setTitle(title)
                .setDescription(msg)
                .setThumbnail(info.getSender().getUser().getAvatarUrl())
                .setColor(Color.decode(hexadecimalColor))
                .setFooter("Comando executado pelo " + ((NejiBot)MainApplication.getBot()).getName());
    }

    public static EmbedBuilder DCM(ReceivedInfo info, String erro, MessageEmbed.Field format) {

        EmbedBuilder builder = new EmbedBuilder();

        builder.setAuthor("Resultados para " + info.getSender().getUser().getName());
        builder.setTitle("Algo deu errado :/ Não foi possivel executar o comando.");

        builder.setDescription(erro);
        builder.setThumbnail(MainApplication.getBot().getJavaDiscordAPI().getSelfUser().getAvatarUrl());
        builder.setColor(Color.red.darker());
        if (format != null) {
            builder.addField(format);
        }
        builder.setFooter("Erro registrado pelo " + ((NejiBot) MainApplication.getBot()).getName());

        return builder;
    }

}
