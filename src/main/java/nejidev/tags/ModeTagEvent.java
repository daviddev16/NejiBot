package nejidev.tags;

import nejidev.api.NejiAPI;
import nejidev.api.emotes.EmoteServerType;
import nejidev.api.listeners.ITagEvent;
import net.dv8tion.jda.api.entities.Message;

import javax.annotation.Nullable;

public class ModeTagEvent implements ITagEvent {

    public void onTaggedMessageEvent(@Nullable Message message) {
        assert message != null;

        if(!NejiAPI.Permissions.checkTagPermissions(message.getMember())) return;

        message.addReaction(NejiAPI.getEmote(EmoteServerType.OK)).queue();
        message.addReaction(NejiAPI.getEmote(EmoteServerType.WARNING)).queue();
        message.addReaction(NejiAPI.getEmote(EmoteServerType.DENIED)).queue();
        NejiAPI.sendTagInfo(message);
    }
}
