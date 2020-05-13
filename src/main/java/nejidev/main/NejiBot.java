package nejidev.main;

import nejidev.api.Bot;
import nejidev.banners.ProgrammingLanguageBanner;
import nejidev.events.RegisterListener;

public class NejiBot extends Bot {

    public static final String BOT_TOKEN = "NzA3Nzg2MzUzOTQwNjI3NDY2.XruVkg.8YNU3gZcFqFhFnn4vINilSt7NEw";
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

       setServerGuild(getJavaDiscordAPI().getGuildById(MainApplication.SERVER_ID));

        getJavaDiscordAPI().addEventListener(new RegisterListener());

    }


}
