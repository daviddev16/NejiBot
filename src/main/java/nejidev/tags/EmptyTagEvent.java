package nejidev.tags;
import nejidev.api.listeners.ITagEvent;
import net.dv8tion.jda.api.entities.Message;

/*evento vazio, ou seja não vai acontecer nada*/
public class EmptyTagEvent implements ITagEvent {

    public void onTaggedMessageEvent(Message message) {}

}
