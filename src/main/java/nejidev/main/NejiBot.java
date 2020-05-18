package nejidev.main;

import nejidev.api.Bot;
import nejidev.api.NejiAPI;
import nejidev.api.commands.CommandManager;
import nejidev.api.tag.Tag;
import nejidev.api.tag.TagManager;
import nejidev.banners.GameEngineBanner;
import nejidev.banners.ProgrammingLanguageBanner;
import nejidev.commands.*;
import nejidev.events.WelcomeListener;

public class NejiBot extends Bot {

    public static final String BOT_TOKEN = "NzA3Nzg2MzUzOTQwNjI3NDY2.Xr8pcQ.zpBy-15XIDXuOTZ5WcY65htO23M";

    private static CommandManager commandManager;

    private static TagManager tagManager;

    public ProgrammingLanguageBanner programmingLanguageBanner;

    public GameEngineBanner gameEngineBanner;

    public NejiBot(){
        super(BOT_TOKEN);
    }

    private NejiBot(String token) {
        super(token);
    }

    public void onConnected() {

        System.out.println("connected.");

        getJavaDiscordAPI().addEventListener(new WelcomeListener());

        tagManager = new TagManager();
        tagManager.attachListener(this);

        commandManager = new CommandManager();
        commandManager.attachListener(this);

        programmingLanguageBanner = new ProgrammingLanguageBanner();
        addBanner(programmingLanguageBanner);

        gameEngineBanner = new GameEngineBanner();
        addBanner(gameEngineBanner);

        NejiAPI.registerCommand(new ClearTagCommand());
        NejiAPI.registerCommand(new HelpCommand());
        NejiAPI.registerCommand(new CountCommand());
        NejiAPI.registerCommand(new AvatarCommand());
        NejiAPI.registerCommand(new MuteCommand());
        NejiAPI.registerCommand(new DesmuteCommand());

        NejiAPI.registerTag(Tag.createTag("feedback", NejiAPI.ok(), NejiAPI.warning(), NejiAPI.denied()));

        NejiAPI.setupActivityUpdater();
    }

    public String getName(){
        return "Ajudante Nejizin";
    }

    public static CommandManager getCommandManager(){
        return commandManager;
    }

    public static TagManager getTagManager() { return tagManager; }


}
