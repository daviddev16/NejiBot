package nejidev.api.tag;

import nejidev.api.app.Bot;
import nejidev.api.listeners.IAttachable;
import nejidev.api.commands.CommandManager;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

public class TagManager extends ListenerAdapter implements IAttachable<Bot> {

    private final List<Tag> tags;

    public TagManager(){
        tags = new ArrayList<>();
    }
    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        if (CommandManager.containsCommandOnMessage(event)) {
            return;
        }

        if (TagToken.containsTag(event.getMessage())) {
            TagToken.compile(getTags(), event.getMessage());
        }
    }

    public List<Tag> getTags() {
        return tags;
    }

    public void attachListener(Bot bot) {
        bot.getJavaDiscordAPI().addEventListener(this);
    }
}
