package nejidev.api;

import nejidev.api.commands.CommandBase;
import nejidev.api.commands.ReceivedInfo;
import nejidev.api.tag.Tag;
import nejidev.api.utils.Utils;
import nejidev.main.MainApplication;
import nejidev.main.NejiBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.annotation.Nullable;
import java.awt.*;
import java.io.*;
import java.util.Objects;
import java.util.Random;

public final class NejiAPI {

    /*mestre role id*/
    public static long MESTRE = 707781835215863939L;

    /*admin role id*/
    public static long ADMIN = 707784420018618398L;

    /*everyone code*/
    public static long EVERYONE = -1L;

    /*silenciado role id*/
    public static long SILENCIADO = 711342722937782293L;

    public interface IBotActivity {

        Activity.ActivityType getType();

        String getMessage();

    }

    public static final Random random = new Random();

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

    public static void registerTag(Tag tag) { NejiBot.getTagManager().getTags().add(tag); }

    public static Emote getEmote(EmoteServerType type){
        return getServerEmote(type.getEmoteName());
    }

    public static @NotNull EmbedBuilder buildMsg(ReceivedInfo info, String title, String hexadecimalColor, String msg){
        return new EmbedBuilder().setAuthor("Resultados para " + info.getSender().getUser().getName())
                .setTitle(title)
                .setDescription(msg)
                .setThumbnail(info.getSender().getUser().getAvatarUrl())
                .setColor(Color.decode(hexadecimalColor))
                .setFooter("Comando executado pelo "  + getSelfName(), info.getSender().getUser().getAvatarUrl());
    }

    public static @NotNull EmbedBuilder buildMsg(ReceivedInfo info, String erro, MessageEmbed.Field format) {

        EmbedBuilder builder = new EmbedBuilder();

        builder.setAuthor("Resultados para " + info.getSender().getUser().getName());
        builder.setTitle("Algo deu errado :/ Não foi possivel executar o comando.");
        builder.setThumbnail(MainApplication.getBot().getJavaDiscordAPI().getSelfUser().getAvatarUrl());
        builder.setDescription(erro);
        builder.setColor(Color.red.darker());
        if (format != null) {
            builder.addField(format);
        }
        builder.setFooter("Erro registrado pelo " + getSelfName(),MainApplication.getBot().getJavaDiscordAPI().getSelfUser().getAvatarUrl());
        return builder;
    }

    public synchronized static void updateActivity(Activity.ActivityType type, String msg){
        MainApplication.getBot().updateActivity(type, msg);
    }

    public synchronized static void updateActivity(IBotActivity botActivity){
        MainApplication.getBot().updateActivity(botActivity.getType(), botActivity.getMessage());
    }

    private static String read(String resourceFile) throws IOException {
        StringBuffer buffer = new StringBuffer();
        InputStream in = Utils.getInputStream("/"+ resourceFile);
        Objects.requireNonNull(in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        while((line = reader.readLine()) != null) {
            buffer.append(line).append('\n');
        }
        return buffer.toString();
    }

    private static JSONArray getActivityMessages() throws IOException {
        JSONObject o = new JSONObject(read("config.json"));
        return o.getJSONArray("activities");
    }

    private static void validateActivityJSON(JSONObject o){
        if(o == null)
            throw new NullPointerException("JSONObject é nulo.");
        if(!o.has("text") || !o.has("type"))
            throw new NullPointerException("JSONObject não possui todos os parametros necessarios.");
    }

    public static void setupActivityUpdater() {
        Thread t = new Thread(() -> {
            try {
                for(;;) {
                    IBotActivity activity = catchRandomActivity();
                    Objects.requireNonNull(activity);
                    updateActivity(activity);
                    Thread.sleep(60 * 1000L);
                }
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        });
        t.start();
    }

    @Nullable
    public synchronized static IBotActivity catchRandomActivity(){
        try {
            JSONArray activities = getActivityMessages();
            Objects.requireNonNull(activities);
            JSONObject randomJSONOBject = activities.getJSONObject(random.nextInt(activities.length()));
            validateActivityJSON(randomJSONOBject);
            return new IBotActivity() {
                public Activity.ActivityType getType() {
                    return Activity.ActivityType.valueOf(randomJSONOBject.getString("type").toUpperCase());
                }
                @Override
                public String getMessage() {
                    return randomJSONOBject.getString("text");
                }
            };
        }catch (IOException e){
            e.printStackTrace();
        }
        return null;
    }

}
