package nejidev.api.tag;
import nejidev.api.listeners.ITagEvent;
import net.dv8tion.jda.api.entities.Message;

public class EmptyTagEvent implements ITagEvent {

    public void onTaggedMessageEvent(Message message) {}

}
