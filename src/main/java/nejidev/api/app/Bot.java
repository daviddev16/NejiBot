package nejidev.api.app;

import nejidev.api.banners.Banner;
import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.ReadyEvent;
import net.dv8tion.jda.api.events.StatusChangeEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import net.dv8tion.jda.api.requests.GatewayIntent;
import net.dv8tion.jda.api.utils.ChunkingFilter;
import org.jetbrains.annotations.NotNull;
import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.EnumSet;
import java.util.List;
import java.util.concurrent.Executors;

public abstract class Bot {

    private JDA javaDiscordAPI;

    private Guild serverGuild;

    private final List<Banner> banners;

    private final String token;

    public Bot(String token) {
        this.token = token;
        banners = new ArrayList<>();
    }

    /*Atualizar status do bot*/
    public synchronized void updateActivity(Activity.ActivityType type, String activity){
        javaDiscordAPI.getPresence().setActivity(Activity.of(type, activity));
    }

    /*adicionar banner ao servidor*/
    public void addBanner(@NotNull Banner banner){
        if(banner.onAwake()) {
            try {
                banners.add(banner);
            }catch (Exception e){
                e.printStackTrace();
            }finally {
                banner.onAdded();
                getJavaDiscordAPI().addEventListener(banner);
            }
        }
    }
    public JDA getJavaDiscordAPI(){
        return javaDiscordAPI;
    }

    public Guild getOfficialGuild(){
        return serverGuild;
    }

    /*essa função será chamada quando o bot for carregado e conectado*/
    public abstract void onConnected();

    /*carregar a JDA*/
    public Bot load(long serverId) throws LoginException {
        JDABuilder builder = JDABuilder.createDefault(token).addEventListeners(new ListenerAdapter() {
            public void onReady(@Nonnull ReadyEvent event) {
                javaDiscordAPI = event.getJDA();
                serverGuild = event.getJDA().getGuildById(serverId);
                onConnected();
            }
        });
        builder.setChunkingFilter(ChunkingFilter.ALL);
        builder.setEnabledIntents(EnumSet.allOf(GatewayIntent.class));
        builder.build();
        return this;
    }


}
