package nejidev.events;

import nejidev.main.NejiBot;
import nejidev.utils.ImageCreator;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.GuildJoinEvent;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.io.IOException;

public class WelcomeListener extends ListenerAdapter  {

    /*enviar banner quando membro entrar no servidor*/
    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
        if(event.getUser().isBot()){
            return;
        }
        TextChannel welcomeChannel = event.getGuild().getTextChannelById(NejiBot.WELCOME_CHANNEL_ID);
        try {
            assert welcomeChannel != null;
            welcomeChannel.sendFile(ImageCreator.createWelcome(event.getUser()), "welcome-" + event.getUser().getName() + ".png").queue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
