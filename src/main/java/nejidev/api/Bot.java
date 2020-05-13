package nejidev.api;

import net.dv8tion.jda.api.JDA;
import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Activity;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.events.StatusChangeEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import org.jetbrains.annotations.NotNull;
import javax.annotation.Nonnull;
import javax.security.auth.login.LoginException;
import java.util.ArrayList;
import java.util.List;

public abstract class Bot {

    private JDA javaDiscordAPI;

    private Guild serverGuild;

    private volatile List<Banner> banners;

    private String token;

    private long serverId;

    public Bot(String token, long serverId) {
        this.token = token;
        this.serverId = serverId;
        banners = new ArrayList<>();
    }

    public void updateActivity(Activity.ActivityType type, String activity){
        javaDiscordAPI.getPresence().setActivity(Activity.of(type, activity));
    }

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

    public void displayAll(){
        for(Banner banner : banners){
            banner.displayBanner();
        }
    }

    public List<Banner> getBanners(){
        return banners;
    }

    public JDA getJavaDiscordAPI(){
        return javaDiscordAPI;
    }

    public Guild getOfficialGuild(){
        return serverGuild;
    }

    public void setServerGuild(Guild guild){
        serverGuild = guild;
    }

    public abstract void onConnected();

    public Bot load() throws LoginException {

        JDABuilder builder = JDABuilder.createDefault(token);
        javaDiscordAPI = builder.build();

        serverGuild = javaDiscordAPI.getGuildById(serverId);
        javaDiscordAPI.addEventListener(new ListenerAdapter() {
            public void onStatusChange(@Nonnull StatusChangeEvent event) {
                if(event.getNewStatus() == JDA.Status.CONNECTED){
                    onConnected();

                }
            }
        });
        return this;
    }


}
