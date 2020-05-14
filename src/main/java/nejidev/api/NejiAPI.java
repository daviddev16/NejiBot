package nejidev.api;

import nejidev.api.commands.CommandBase;
import nejidev.api.commands.ReceivedInfo;
import nejidev.main.MainApplication;
import nejidev.main.NejiBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.entities.TextChannel;

import java.awt.*;

public final class NejiAPI {

    public static final long REGISTER_CHANNEL_ID = 708748306049663067L;

    public static final long WELCOME_CHANNEL_ID = 708726890231365722L;

    public static Banner getServerBanner(BannerType bannerType){

        NejiBot nejiBot = (NejiBot) MainApplication.getBot();

        switch (bannerType) {
            case PROGRAMMING_LANGUAGE:
                return nejiBot.programmingLanguageBanner;
            case GAME_ENGINE:
                return nejiBot.gameEngineBanner;
            default:
                throw new IllegalStateException("Unexpected value: " + bannerType);
        }

    }

    public static Emote getServerEmote(String name){

        NejiBot nejiBot = (NejiBot) MainApplication.getBot();

        return nejiBot.getJavaDiscordAPI().getEmotes()
                .stream()
                .filter(emote -> emote.getName().equalsIgnoreCase(name))
                .findAny().orElseThrow(() -> new NullPointerException("Não foi possivel achar o emote."));

    }

    public static TextChannel getServerTextChannel(long textChannelId){

        NejiBot nejiBot = (NejiBot) MainApplication.getBot();
        return nejiBot.getJavaDiscordAPI().getTextChannelById(textChannelId);

    }

    public static Guild getServerGuild(){
        return ((NejiBot)MainApplication.getBot()).getOfficialGuild();
    }

    public static String getSelfName(){
        return ((NejiBot)MainApplication.getBot()).getName();
    }

    public static Emote getServerEmoteByRole(ReactionRole role){
        return getServerEmote(role.getEmoteName());
    }

    public static void registerCommand(CommandBase command){
        NejiBot.getCommandManager().registerCommand(command);
    }

    public static Emote ok(){
        return getServerEmote("ok");
    }

    public static Emote warning(){
        return getServerEmote("warning");
    }

    public static Emote denied(){
        return getServerEmote("denied");
    }

    /*emote "happy" de dentro do servidor*/
    public static Emote happy(){
        return getServerEmote("happy");
    }

    public static EmbedBuilder buildMsg(ReceivedInfo info, String title, String hexadecimalColor, String msg){
        return new EmbedBuilder().setAuthor("Resultados para " + info.getSender().getUser().getName())
                .setTitle(title)
                .setDescription(msg)
                .setThumbnail(info.getSender().getUser().getAvatarUrl())
                .setColor(Color.decode(hexadecimalColor))
                .setFooter("Comando executado pelo "  + getSelfName());
    }

    public static EmbedBuilder buildMsg(ReceivedInfo info, String erro, MessageEmbed.Field format) {

        EmbedBuilder builder = new EmbedBuilder();

        builder.setAuthor("Resultados para " + info.getSender().getUser().getName());
        builder.setTitle("Algo deu errado :/ Não foi possivel executar o comando.");

        builder.setDescription(erro);
        builder.setThumbnail(MainApplication.getBot().getJavaDiscordAPI().getSelfUser().getAvatarUrl());
        builder.setColor(Color.red.darker());
        if (format != null) {
            builder.addField(format);
        }
        builder.setFooter("Erro registrado pelo " + getSelfName());

        return builder;
    }
}
