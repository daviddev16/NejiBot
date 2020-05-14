package nejidev.commands;

import nejidev.api.commands.CommandBase;
import nejidev.api.commands.ReceivedInfo;
import nejidev.utils.ImageCreator;

public class WelcomeLogCommand extends CommandBase {

    public WelcomeLogCommand(){
        super("w");
    }

    public WelcomeLogCommand(String command) {
        super(command);
    }

    @Override
    public boolean execute(ReceivedInfo receivedInfo) {

        try {
            receivedInfo.getReceiverEvent().getTextChannel().sendFile(ImageCreator.createWelcome(receivedInfo.getSender().getUser()), "msmsms.png").queue();
        }catch (Exception e){
            e.printStackTrace();
        }
        return true;
    }
}
