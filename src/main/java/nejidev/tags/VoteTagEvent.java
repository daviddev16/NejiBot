package nejidev.tags;

import nejidev.api.NejiAPI;
import nejidev.api.emotes.EmoteServerType;
import nejidev.api.listeners.ITagEvent;
import net.dv8tion.jda.api.entities.Message;

public class VoteTagEvent implements ITagEvent {

    public void onTaggedMessageEvent(Message message) {
        assert message != null;

        if (!NejiAPI.Permissions.checkTagPermissions(message.getMember())) return;

        message.addReaction(NejiAPI.getEmote(EmoteServerType.UP_VOTE)).queue();
        message.addReaction(NejiAPI.getEmote(EmoteServerType.DOWN_VOTE)).queue();
        NejiAPI.sendTagInfo(message);
    }
}