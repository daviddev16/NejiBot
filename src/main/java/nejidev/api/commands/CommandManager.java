package nejidev.api.commands;

import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*Classe responsavel pelo gerenciamento dos comandos no chat,
* serve para adicionar comandos novos e fazer a pesquisa de comando */
public class CommandManager extends ListenerAdapter {

    /*lista de comandos*/
    private final List<BaseCommand> commands;

    public CommandManager(){
        commands = new ArrayList<>();
    }

    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        /*verificar se quem enviou a mensagem não é um bot*/
        if(Objects.requireNonNull(event.getMember()).getUser().isBot()){
            return;
        }
        if(containsCommandOnMessage(event)){

            /*fazer a query do comando e enviar o ReceivedInfo para o executor do comando*/
            ReceivedInfo.query(event, receivedInfo -> commands.forEach(cmd -> {
                if(cmd.accept(receivedInfo)){

                    /*executar comando assim que for achado pela query*/
                    cmd.execute(receivedInfo);
                }
            }));
        }
    }
    /*verificar se ha um comando passado no chat*/
    private boolean containsCommandOnMessage(MessageReceivedEvent event){
        return event.getMessage().getContentRaw().startsWith(BaseCommand.COMMAND_PREFIX);
    }

    /*registrar comando*/
    public void registerCommand(BaseCommand command){
        commands.add(command);
    }

    public List<BaseCommand> getCommands(){
        return commands;
    }
}
