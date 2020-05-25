package nejidev.tags;

import nejidev.api.NejiAPI;
import nejidev.api.emotes.EmoteServerType;
import nejidev.api.listeners.ITagEvent;
import nejidev.api.tag.TagQueryResult;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

public class SickoTagEvent implements ITagEvent {

    public void onTaggedMessageEvent(@Nullable Message message,@NotNull TagQueryResult tagQueryResult) {
        assert message != null;

        if(!NejiAPI.Permissions.checkTagPermissions(message.getMember())) return;

        message.addReaction(NejiAPI.getEmote(EmoteServerType.SICKO)).queue();

        NejiAPI.sendTagInfo(message);
    }
}
