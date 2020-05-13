package nejidev.main;

import nejidev.api.Bot;
import nejidev.banners.GameEngineBanner;
import nejidev.banners.ProgrammingLanguageBanner;
import nejidev.events.RegisterListener;

public class NejiBot extends Bot {

    public static final String BOT_TOKEN = "token";

    public static final long REGISTER_CHANNEL_ID = 708748306049663067L;

    public static final long LISTENING_SERVER_ID = 707778877493215364L;

    public NejiBot(){
        super(BOT_TOKEN, LISTENING_SERVER_ID);
    }

    private NejiBot(String token, long serverId) {
        super(token, serverId);

    }

    public void onConnected() {
        System.out.println("connected.");

        addBanner(new ProgrammingLanguageBanner());
        addBanner(new GameEngineBanner());

       setServerGuild(getJavaDiscordAPI().getGuildById(MainApplication.SERVER_ID));

        getJavaDiscordAPI().addEventListener(new RegisterListener());

    }


}
