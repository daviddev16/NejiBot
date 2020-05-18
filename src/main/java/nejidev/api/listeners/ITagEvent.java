package nejidev.api.listeners;

import net.dv8tion.jda.api.entities.Message;

public interface ITagEvent {

    void onTaggedMessageEvent(Message message);

}
