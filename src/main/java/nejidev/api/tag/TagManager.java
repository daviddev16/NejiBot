package nejidev.api.tag;

import nejidev.api.commands.CommandManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import javax.annotation.Nonnull;

public class TagManager extends ListenerAdapter {

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {

        if(CommandManager.containsCommandOnMessage(event)){
            return;
        }



    }
}
