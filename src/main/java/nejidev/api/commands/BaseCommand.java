package nejidev.api.commands;

public abstract class BaseCommand {

    /*Prefixo usado pelo usuario para chamar o comando*/
    public static final String COMMAND_PREFIX = ">";

    private final String command;

    public BaseCommand(String command){
        this.command = command;
    }

    /*verificar se o comando bate com as informações recebidas*/
    public boolean accept(ReceivedInfo receivedInfo) {
        return getCommand().equalsIgnoreCase(receivedInfo.getCommand());
    }

    /*executor do comando*/
    public abstract void execute(ReceivedInfo receivedInfo);

    public String getCommand(){
        return this.command;
    }

}
