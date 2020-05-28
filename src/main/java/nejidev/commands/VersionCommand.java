package nejidev.commands;

import nejidev.api.NejiAPI;
import nejidev.api.commands.CommandBase;
import nejidev.api.commands.ReceivedInfo;
import nejidev.api.commands.miscs.Category;
import nejidev.api.commands.miscs.CommandCategory;

@CommandCategory(category = Category.SERVER)
public class VersionCommand extends CommandBase {

    public VersionCommand() {
        super("version");
    }

    public VersionCommand(String command) {
        super(command);
    }

    public boolean execute(ReceivedInfo receivedInfo) {

        sendSimple(receivedInfo, "Minha versão atual é: " + NejiAPI.VERSION).queue();

        return true;
    }
}