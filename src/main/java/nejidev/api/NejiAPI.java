package nejidev.api;

import nejidev.api.banners.Banner;
import nejidev.api.banners.ReactionRole;
import nejidev.api.commands.CommandBase;
import nejidev.api.commands.ReceivedInfo;
import nejidev.api.emotes.EmoteServerType;
import nejidev.api.tag.Tag;
import nejidev.api.utils.Schedule;
import nejidev.api.utils.Utils;
import nejidev.banners.BannerType;
import nejidev.main.MainApplication;
import nejidev.main.NejiBot;
import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.*;
import org.jetbrains.annotations.NotNull;
import org.json.JSONArray;
import org.json.JSONObject;

import javax.annotation.Nullable;
import java.awt.*;
import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.time.Duration;
import java.util.Objects;
import java.util.Random;

public final class NejiAPI {

    public static final class Permissions {
        /*roles*/
        public static long MESTRE = 707781835215863939L;
        public static long ADMIN = 707784420018618398L;
        public static long BOOSTER = 711843254324166689L;
        public static long SILENCIADO = 711342722937782293L;
        public static long DESENVOLVEDOR = 707784153252495424L;
        public static long GAME_DESIGNER = 707784246667903046L;
        public static long EVERYONE = -1L;

        public static boolean checkMasterPermissions(Member member){
            return NejiAPI.checkPermission(member, ADMIN, MESTRE);
        }

        public static boolean checkTagPermissions(Member member){
            //return NejiAPI.checkPermission(member, ADMIN, MESTRE, BOOSTER);
            return NejiAPI.checkPermission(member, EVERYONE);
        }

        public static boolean checkIssuesPermission(Member member){
            //return NejiAPI.checkPermission(member, MESTRE, ADMIN, GAME_DESIGNER, DESENVOLVEDOR);
            return NejiAPI.checkPermission(member, EVERYONE);
        }


    }

    public interface IBotActivity {

        Activity.ActivityType getType();

        String getMessage();

    }

    public static final Random random = new Random();

    /*canal de registro do membro*/
    public static final long REGISTER_CHANNEL_ID = 708748306049663067L;

    /*canal de bem-vindas*/
    public static final long WELCOME_CHANNEL_ID = 708726890231365722L;

    /*canal de issues*/
    public static final long ISSUE_CHANNEL_ID = 709283806925815908L;

    /*acessar os banners do servidor*/
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

    /*pegar um emote do servidor pelo nome*/
    public static Emote getServerEmote(String name){

        NejiBot nejiBot = (NejiBot) MainApplication.getBot();

        return nejiBot.getJavaDiscordAPI().getEmotes()
                .stream()
                .filter(emote -> emote.getName().equalsIgnoreCase(name))
                .findAny().orElseThrow(() -> new NullPointerException("Não foi possivel achar o emote."));

    }

    /*acessar um canal de texto pelo id*/
    public static TextChannel getServerTextChannel(long textChannelId){

        NejiBot nejiBot = (NejiBot) MainApplication.getBot();
        return nejiBot.getJavaDiscordAPI().getTextChannelById(textChannelId);

    }

    /*retornar a instancia da Guild do servidor vinda do bot*/
    public static Guild getServerGuild(){
        return MainApplication.getBot().getOfficialGuild();
    }

    /*pegar nome do bot ativo*/
    public static String getSelfName(){
        return ((NejiBot)MainApplication.getBot()).getName();
    }

    /*acessar o emote de um ReactionRole*/
    public static Emote getServerEmoteByRole(ReactionRole role){
        return getServerEmote(role.getEmoteName());
    }

    /*registrar um comando no bot*/
    public static void registerCommand(CommandBase command){
        NejiBot.getCommandManager().registerCommand(command);
    }

    /*registrar uma tag no bot*/
    public static void registerTag(Tag tag) { NejiBot.getTagManager().getTags().add(tag); }

    /*pegar um emote pelo tipo dele no servidor*/
    public static Emote getEmote(EmoteServerType type){
        return getServerEmote(type.getEmoteName());
    }

    /*criar uma mensagem personalizada pre-criada.*/
    public static @NotNull EmbedBuilder buildMsg(ReceivedInfo info, String title, String hexadecimalColor, String msg){
        return new EmbedBuilder().setAuthor("Resultados para " + info.getSender().getUser().getName())
                .setTitle(title)
                .setDescription(msg)
                .setThumbnail(info.getSender().getUser().getAvatarUrl())
                .setColor(Color.decode(hexadecimalColor))
                .setFooter("Comando executado pelo "  + getSelfName(), info.getSender().getUser().getAvatarUrl());
    }

    /*criar uma mensagem de erro pre-criada.*/
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

    /*atualizar a atividade/status de presença do bot*/
    public synchronized static void updateActivity(Activity.ActivityType type, String msg){
        MainApplication.getBot().updateActivity(type, msg);
    }

    /*atualizar a atividade/status de presença do bot*/
    public synchronized static void updateActivity(IBotActivity botActivity){
        MainApplication.getBot().updateActivity(botActivity.getType(), botActivity.getMessage());
    }

    /*ler um arquivo legivel pelo InputStream dele*/
    public static String read(String resourceFile) throws IOException {
        StringBuilder buffer = new StringBuilder();
        InputStream in = Utils.getInputStream("/"+ resourceFile);
        Objects.requireNonNull(in);
        BufferedReader reader = new BufferedReader(new InputStreamReader(in));
        String line;
        while((line = reader.readLine()) != null) {
            buffer.append(line).append('\n');
        }
        return buffer.toString();
    }

    /*acessar o JSONArray das atividades pre-criadas nas
    * configurações*/
    private static JSONArray getActivityMessages() throws IOException {
        JSONObject o = new JSONObject(read("config.json"));
        return o.getJSONArray("activities");
    }

    /*validar atividades*/
    private static void validateActivityJSON(JSONObject o){
        if(o == null)
            throw new NullPointerException("JSONObject é nulo.");
        if(!o.has("text") || !o.has("type"))
            throw new NullPointerException("JSONObject não possui todos os parametros necessarios.");
    }

    /**
     *
     * criar thread para gerenciar o status do bot,
     * atualizando os status a cada 1 min.
     *
     */
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

    /*obter uma mensagem de status aleatoria*/
    @Nullable
    private synchronized static IBotActivity catchRandomActivity(){
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

    /*checar permissões do membro*/
    public static boolean checkPermission(Member member, long... rolePermissions){
        for(long roleIds : rolePermissions){
            if(roleIds == Permissions.EVERYONE) return true;
            Role roleById = NejiAPI.getServerGuild().getRoleById(roleIds);
            if(member.getRoles().contains(roleById)) return true;
        }
        return false;
    }

    /*enviar mensagem de novidade quando alguma tag for mencionada*/
    public static void sendTagInfo(Message tagMsg){
        MessageEmbed msg = new EmbedBuilder()
                .setColor(Color.magenta)
                .setTitle("Tags [Novidade]")
                .setDescription("Há uma tag na sua mensagem, isto significa que os membros" +
                        "podem interagir com a sua mensagem !")
                .build();
        NejiAPI.sendTemporaryMessage(msg, tagMsg.getTextChannel(), Duration.ofSeconds(5L));
    }

    /*enviar uma mensagem temporaria.*/
    public static void sendTemporaryMessage(MessageEmbed embed, TextChannel channel, Duration duration){
        channel.sendMessage(embed).queue(message -> Schedule.newScheduleEvent(duration, () -> channel.deleteMessageById(message.getIdLong()).queue()));
    }
    public static void sendTemporaryMessage(MessageEmbed embed, TextChannel channel, Duration duration, Emote emote){
        channel.sendMessage(embed).queue(message -> {
            message.addReaction(emote).queue();
            Schedule.newScheduleEvent(duration, () -> channel.deleteMessageById(message.getIdLong()).queue());
        });
    }

    public static Message findIssue(String id){
        TextChannel issueChannel = getServerGuild().getTextChannelById(ISSUE_CHANNEL_ID);
        Objects.requireNonNull(issueChannel);
        return issueChannel.retrieveMessageById(id).complete();
    }

    public static void quickReact(Message message, Emote emote){
        message.addReaction(emote).queue();
    }


    @Nullable
    public static Member findMember(String userId){
        for(Member members : MainApplication.getBot().getOfficialGuild().getMembers()){
            if(members.getUser().getId().equalsIgnoreCase(userId)){
                return members;
            }
        }
        return null;
    }

}
