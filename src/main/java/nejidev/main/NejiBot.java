package nejidev.main;

import nejidev.api.Banner;
import nejidev.api.Bot;
import nejidev.api.commands.CommandManager;
import nejidev.banners.BannerType;
import nejidev.banners.GameEngineBanner;
import nejidev.banners.ProgrammingLanguageBanner;
import nejidev.commands.ClearTagCommand;
import nejidev.commands.WelcomeLogCommand;
import nejidev.events.WelcomeListener;

public class NejiBot extends Bot {

    public static final String BOT_TOKEN = "<token>";

    public static final long REGISTER_CHANNEL_ID = 708748306049663067L;

    public static final long WELCOME_CHANNEL_ID = 708726890231365722L;

    private static CommandManager commandManager;

    private ProgrammingLanguageBanner programmingLanguageBanner;

    private GameEngineBanner gameEngineBanner;

    public NejiBot(){
        super(BOT_TOKEN, MainApplication.SERVER_ID);
    }

    private NejiBot(String token, long serverId) {
        super(token, serverId);
    }

    public void onConnected() {
        System.out.println("connected.");

        setServerGuild(getJavaDiscordAPI().getGuildById(MainApplication.SERVER_ID));

        getJavaDiscordAPI().addEventListener(new WelcomeListener());

        commandManager = new CommandManager();
        commandManager.attachListener(this);

        programmingLanguageBanner = new ProgrammingLanguageBanner();
        addBanner(programmingLanguageBanner);

        gameEngineBanner = new GameEngineBanner();
        addBanner(gameEngineBanner);

        commandManager.registerCommand(new ClearTagCommand());
        commandManager.registerCommand(new WelcomeLogCommand());

    }

    public Banner getServerBanner(BannerType bannerType){
        switch (bannerType) {
            case PROGRAMMING_LANGUAGE:
                return programmingLanguageBanner;
            case GAME_ENGINE:
                return gameEngineBanner;
            default:
                throw new IllegalStateException("Unexpected value: " + bannerType);
        }
    }

    public String getName(){
        return "Ajudante Nejizin";
    }

    public static CommandManager getCommandManager(){
        return commandManager;
    }


}
