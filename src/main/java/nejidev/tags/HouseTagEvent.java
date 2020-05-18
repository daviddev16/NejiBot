package nejidev.tags;

import nejidev.api.emotes.EmoteServerType;
import nejidev.api.NejiAPI;
import nejidev.api.listeners.ITagEvent;
import net.dv8tion.jda.api.entities.Message;


/*
 * adicionar reações a mensagem com a tag [houses]
 * */
public class HouseTagEvent implements ITagEvent {

    public void onTaggedMessageEvent(Message message) {

        assert message != null;
        message.addReaction(NejiAPI.getEmote(EmoteServerType.HOUSE_BALANCE)).queue();
        message.addReaction(NejiAPI.getEmote(EmoteServerType.HOUSE_BRILIANCE)).queue();
        message.addReaction(NejiAPI.getEmote(EmoteServerType.HOUSE_BRAVERY)).queue();
    }
}
