package nejidev.api.listeners;

import javax.annotation.Nullable;
import net.dv8tion.jda.api.entities.Message;

public interface ITagEvent {

    void onTaggedMessageEvent(@Nullable Message message);

}
