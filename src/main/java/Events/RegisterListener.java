package Events;


import Main.Manager;
import Main.Rule;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionAddEvent;
import net.dv8tion.jda.api.events.message.guild.react.GuildMessageReactionRemoveEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.io.IOException;

public class RegisterListener extends ListenerAdapter {


    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {

        if(event.getMember().getUser().isBot()){
           return;
        }



    }

    @Deprecated
    public static void tempCommand(MessageReceivedEvent event){

        if(event.getMessage().getContentRaw().equals("-b")){
            Manager.PROGRAMMING_LANGUAGE_MESSAGE_ID = event.getMessage().getIdLong();
            try {
                event.getTextChannel().sendFile(RegisterListener.class.getResource("/banner_register.png").openStream(), "banner.png").queue(message -> {
                    for(Rule plRules : Manager.programmingLanguageRules){
                        message.addReaction(Manager.getEmoteByName(plRules.getEmoteName(), message.getGuild())).queue();
                    }
                });
            } catch (IOException e) {
                e.printStackTrace();
            }

        }

    }


    @Override
    public void onGuildMessageReactionAdd(@Nonnull GuildMessageReactionAddEvent event) {
        if(event.getUser().isBot()){
            return;
        }
        if(Manager.catchRegistrationMessage(event.getChannel(), event.getMessageIdLong())){
            Rule foundRule = Manager.findMyRuleByEmote(event.getReactionEmote().getEmote());
            if(foundRule != null){
                foundRule.addMember(event.getMember(), event.getGuild());
            }
        }
    }

    @Override
    public void onGuildMessageReactionRemove(@Nonnull GuildMessageReactionRemoveEvent event) {

        if (event.getUser().isBot()) {
            return;
        }
        if (Manager.catchRegistrationMessage(event.getChannel(), event.getMessageIdLong())) {
            Rule foundRule = Manager.findMyRuleByEmote(event.getReactionEmote().getEmote());
            if (foundRule != null) {
                foundRule.removeMember(event.getMember(), event.getGuild());
            }
        }
    }

}
