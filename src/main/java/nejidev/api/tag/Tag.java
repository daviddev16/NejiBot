package nejidev.api.tag;

import nejidev.api.listeners.ITagEvent;
import net.dv8tion.jda.api.entities.Message;

import java.util.function.Supplier;

public class Tag {

    private final String key;
    private final ITagEvent event;

    public Tag(String key, ITagEvent event) {
        this.key = key;
        this.event = event;
    }

    public String getKey() {
        return key;
    }

    public void callEvent(Message message) { event.onTaggedMessageEvent(message); }

    public static Tag createTag(String key, Supplier<ITagEvent> event){
        ITagEvent tagEvent = event.get();
        if(tagEvent == null){
            tagEvent = new EmptyTagEvent();
        }
        return new Tag(key, tagEvent);
    }
}
