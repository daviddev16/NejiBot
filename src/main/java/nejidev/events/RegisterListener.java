package nejidev.events;


import nejidev.main.MainApplication;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.Objects;

public class RegisterListener extends ListenerAdapter {

/*temporary*/
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {

        if (!Objects.requireNonNull(event.getMember()).getUser().isBot()) {
            if (event.getMessage().getContentRaw().equalsIgnoreCase("-c")) {
                MainApplication.getBot().getBanners().get(1).displayBanner();
            }
        }


    }



}
