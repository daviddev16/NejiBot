package nejidev.tags;

import nejidev.api.NejiAPI;
import nejidev.api.listeners.ITagEvent;
import net.dv8tion.jda.api.entities.Message;
import javax.annotation.Nullable;


/*Facebook reactions tag event*/
public class FacebookReactionsTagEvent implements ITagEvent {

    public void onTaggedMessageEvent(@Nullable Message message) {
        assert message != null;

        if(!NejiAPI.Permissions.checkTagPermissions(message.getMember())) return;

        message.addReaction("U+1F44D").queue();
        message.addReaction("U+2764").queue();
        message.addReaction("U+1F602").queue();
        message.addReaction("U+1F62E").queue();
        message.addReaction("U+1F61E").queue();
        message.addReaction("U+1F612").queue();
        NejiAPI.sendTagInfo(message);
    }
}
