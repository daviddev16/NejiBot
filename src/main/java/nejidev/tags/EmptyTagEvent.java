package nejidev.tags;
import nejidev.api.listeners.ITagEvent;
import nejidev.api.tag.TagQueryResult;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;

/*evento vazio, ou seja não vai acontecer nada*/
public class EmptyTagEvent implements ITagEvent {

    public void onTaggedMessageEvent(Message message, @NotNull TagQueryResult tagQueryResult) {}

}
