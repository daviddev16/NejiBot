package nejidev.tags;

import nejidev.api.emotes.EmoteServerType;
import nejidev.api.NejiAPI;
import nejidev.api.listeners.ITagEvent;
import net.dv8tion.jda.api.entities.Message;

/*
* adicionar reações a mensagem com a tag [feedback]
* */
public class FeedbackTagEvent implements ITagEvent  {

    public void onTaggedMessageEvent(Message message) {
        assert message != null;
        message.addReaction(NejiAPI.getEmote(EmoteServerType.YES)).queue();
        message.addReaction(NejiAPI.getEmote(EmoteServerType.NO)).queue();
        NejiAPI.sendTagInfo(message);
    }
}
