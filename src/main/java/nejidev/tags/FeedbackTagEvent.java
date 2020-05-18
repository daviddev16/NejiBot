package nejidev.tags;

import nejidev.api.EmoteServerType;
import nejidev.api.NejiAPI;
import nejidev.api.listeners.ITagEvent;
import net.dv8tion.jda.api.entities.Message;

public class FeedbackTagEvent implements ITagEvent  {

    public void onTaggedMessageEvent(Message message) {
        message.addReaction(NejiAPI.getEmote(EmoteServerType.YES)).queue();
        message.addReaction(NejiAPI.getEmote(EmoteServerType.NO)).queue();
    }
}
