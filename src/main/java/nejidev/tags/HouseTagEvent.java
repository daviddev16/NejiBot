package nejidev.tags;

import nejidev.api.NejiAPI;
import nejidev.api.emotes.EmoteServerType;
import nejidev.api.listeners.ITagEvent;
import nejidev.api.tag.TagQueryResult;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;


/*
 * adicionar reações a mensagem com a tag [houses]
 * */
public class HouseTagEvent implements ITagEvent {

    public void onTaggedMessageEvent(Message message,@NotNull TagQueryResult tagQueryResult) {
        assert message != null;

        if(!NejiAPI.Permissions.checkTagPermissions(message.getMember())) return;

        message.addReaction(NejiAPI.getEmote(EmoteServerType.HOUSE_BALANCE)).queue();
        message.addReaction(NejiAPI.getEmote(EmoteServerType.HOUSE_BRILIANCE)).queue();
        message.addReaction(NejiAPI.getEmote(EmoteServerType.HOUSE_BRAVERY)).queue();
        NejiAPI.sendTagInfo(message);
    }
}
