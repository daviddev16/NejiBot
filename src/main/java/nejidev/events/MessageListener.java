package nejidev.events;

import nejidev.api.NejiAPI;
import nejidev.api.database.MemberElementType;
import nejidev.api.database.NejiDatabase;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.Objects;
import java.util.concurrent.atomic.AtomicBoolean;

public class MessageListener extends ListenerAdapter {

    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {

        if(event.getMember().getUser().isBot()) return;

        Objects.requireNonNull(event.getMessage().getCategory());

        if(event.getMessage().getCategory().getIdLong() == NejiAPI.DEVELOPMENT_CATEGORY_ID) {
            if(deepSearch(event)){

                int amount = (event.getMessage().getContentRaw().length() / 2);
                NejiDatabase.updateMember(event.getMember(), MemberElementType.RANK, amount);

            }
        }
    }

    public static boolean deepSearch(MessageReceivedEvent event){
        assert event != null;

        String currentMessage = event.getMessage().getContentRaw();

        if(currentMessage.length() <= 2){
            return false;
        }

        AtomicBoolean result = new AtomicBoolean(true);

        event.getTextChannel().getHistoryBefore(event.getMessage(), 5).queue(history -> {

            history.getRetrievedHistory().forEach(historyMessage -> {

                if(compareString(currentMessage, historyMessage.getContentRaw())){
                    result.set(false);
                }

            });
        });

        return result.get();
    }

    private static boolean compareString(String s1, String s2){
        return s1.replaceAll("\\+s", "").trim().equalsIgnoreCase(s2.replaceAll("\\+s", "").trim());
    }

}
