package Events;


import Main.Manager;
import Main.Rule;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.events.message.react.MessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.io.IOException;

public class RegisterListener extends ListenerAdapter {

    @Deprecated
    public static void tempCommand(MessageReceivedEvent event){

        if(event.getMessage().getContentRaw().equals("-b")){
            Manager.PROGRAMMING_LANGUAGE_MESSAGE_ID = event.getMessage().getIdLong();
            try {
                event.getTextChannel().sendFile(RegisterListener.class.getResource("/banner_register.png").openStream(), "banner.png").queue(message -> {
                    for(Rule plRules : Manager.getInstance().getRules()){
                        message.addReaction(Manager.getInstance().getEmoteByName(plRules.getEmoteName(), message.getGuild())).queue();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    @Override
    public void onGuildMessageReactionAdd(@Nonnull GuildMessageReactionAddEvent event) {
        if(event.getUser().isBot()) return;
        if(Manager.getInstance().catchRegistrationMessage(event.getChannel(), event.getMessageIdLong())){
            Rule foundRule = Manager.getInstance().findMyRuleByEmote(event.getReactionEmote().getEmote());
            if(foundRule != null){
                foundRule.addMember(event.getMember(), event.getGuild());
            }
        }
    }

    /* I don't know why, but it's throwing up NullPointerException when I tried to get the member object. */
    /*
    @Override
    public void onGuildMessageReactionRemove(@Nonnull GuildMessageReactionRemoveEvent event) {
        if (event.getUser().isBot()) return;
        if (Manager.catchRegistrationMessage(event.getChannel(), event.getMessageIdLong())) {
            Rule foundRule = Manager.findMyRuleByEmote(event.getReactionEmote().getEmote());
            if (foundRule != null) {
                foundRule.removeMember(event.getMember(), event.getGuild());
            }
        }
    }*/


}
