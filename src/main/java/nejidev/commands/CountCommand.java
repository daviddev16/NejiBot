package nejidev.commands;

import nejidev.api.commands.miscs.Category;
import nejidev.api.commands.miscs.CommandCategory;
import nejidev.api.emotes.EmoteServerType;
import nejidev.api.NejiAPI;
import nejidev.api.commands.CommandBase;
import nejidev.api.commands.ReceivedInfo;

@CommandCategory(category = Category.FUN)
public class CountCommand extends CommandBase {

    public CountCommand() {
        super("count");
    }

    public CountCommand(String command) {
        super(command);
    }

    public boolean execute(ReceivedInfo receivedInfo) {

        int countOfMembers = receivedInfo.getSender().getGuild().getMemberCount();
        sendSuccess(receivedInfo,
                "Contador",
                "#884EA0",
                "O servidor possui atualmente " + countOfMembers + ". Aumente o numero de membros chamando seus amigos para o servidor!");

        return true;
    }
}