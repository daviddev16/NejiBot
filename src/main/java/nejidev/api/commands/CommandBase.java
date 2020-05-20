package nejidev.api.commands;

import nejidev.api.NejiAPI;
import nejidev.api.commands.miscs.Category;
import nejidev.api.commands.miscs.CommandCategory;
import nejidev.api.commands.miscs.ICategory;

/*classe responsavel por ser a base de todos os comando do bot*/
@CommandCategory(category = Category.SERVER)
public abstract class CommandBase extends CommandOutput implements ICategory {

    /*Prefixo usado pelo usuario para chamar o comando*/
    public static final String COMMAND_PREFIX = "!";

    private final String command;

    public CommandBase(String command){
        this.command = command;
    }

    /*verificar se o comando bate com as informações recebidas*/
    public boolean accept(ReceivedInfo receivedInfo) {
        return getCommand().equalsIgnoreCase(receivedInfo.getCommand());
    }

    /*executor do comando*/
    public abstract boolean execute(ReceivedInfo receivedInfo);

    public String getCommand(){
        return this.command;
    }

    public boolean checkArgs(String[] args, int requiredCount){
        return args.length == requiredCount;
    }


}
