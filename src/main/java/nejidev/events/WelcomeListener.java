package nejidev.events;

import nejidev.api.NejiAPI;
import nejidev.utils.ImageCreator;
import net.dv8tion.jda.api.entities.Role;
import net.dv8tion.jda.api.entities.TextChannel;
import net.dv8tion.jda.api.events.guild.member.GuildMemberJoinEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import javax.annotation.Nonnull;
import java.io.IOException;
import java.util.Objects;

/*listener responsavel pela entrada de usuarios no servidor*/
public class WelcomeListener extends ListenerAdapter {

    /*enviar banner quando membro entrar no servidor*/
    @Override
    public void onGuildMemberJoin(@Nonnull GuildMemberJoinEvent event) {
        Role playerRole = NejiAPI.getServerGuild().getRoleById(707783643459878923L);
        Objects.requireNonNull(playerRole);
        NejiAPI.getServerGuild().addRoleToMember(event.getMember(), playerRole).queue();
        TextChannel welcomeChannel = NejiAPI.getServerTextChannel(NejiAPI.WELCOME_CHANNEL_ID);
        try {
            assert welcomeChannel != null;
            welcomeChannel.sendFile(ImageCreator.createWelcome(event.getUser()), "welcome-" + event.getUser().getName() + ".png").queue();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
