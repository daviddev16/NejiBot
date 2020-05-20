package nejidev.commands;

import nejidev.api.commands.miscs.Category;
import nejidev.api.commands.miscs.CommandCategory;
import nejidev.api.emotes.EmoteServerType;
import nejidev.api.NejiAPI;
import nejidev.api.commands.CommandBase;
import nejidev.api.commands.ReceivedInfo;

@CommandCategory(category = Category.FUNDAMENTAL)
public class HelpCommand extends CommandBase {

    public HelpCommand(){
        super("help");
    }

    public HelpCommand(String command){
        super(command);
    }

    public boolean execute(ReceivedInfo receivedInfo) {

        sendSuccess(receivedInfo,
                "Ajuda",
                "#8DFF33",
                "Caso você esteja interessado em ver os comandos do bot, " +
                "entre em \"https://github.com/daviddev16/NejiBot/wiki/Help\" la você poderá ver os comandos com mais clareza.");

        return true;
    }
}
