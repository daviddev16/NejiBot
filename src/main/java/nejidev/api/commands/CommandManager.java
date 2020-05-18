package nejidev.api.commands;

import nejidev.api.Bot;
import nejidev.api.IAttachable;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;
import javax.annotation.Nonnull;
import java.time.Duration;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

/*Classe responsavel pelo gerenciamento dos comandos no chat,
* serve para adicionar comandos novos e fazer a pesquisa de comando */
public class CommandManager extends ListenerAdapter implements IAttachable<Bot> {

    /*lista de comandos*/
    private final List<CommandBase> commands;

    public CommandManager(){
        commands = new ArrayList<>();
    }

    @Override
    public void onMessageReceived(@Nonnull MessageReceivedEvent event) {
        /*verificar se quem enviou a mensagem não é um bot*/
        Objects.requireNonNull(event.getMember());
        if(event.getMember().getUser().isBot()){
            return;
        }
        if(containsCommandOnMessage(event)){
            /*fazer a query do comando e enviar o ReceivedInfo para o executor do comando*/
            ReceivedInfo.query(event, receivedInfo -> commands.forEach(cmd -> {
                if(cmd.accept(receivedInfo)){

                    /*executar comando assim que for achado pela query*/
                    if(cmd.execute(receivedInfo)) {
                        event.getTextChannel().deleteMessageById(event.getMessageId()).delay(Duration.ofSeconds(1L)).queue();
                    }
                }
            }));
        }
    }
    /*verificar se ha um comando passado no chat*/
    public static boolean containsCommandOnMessage(MessageReceivedEvent event){
        return event.getMessage().getContentRaw().startsWith(CommandBase.COMMAND_PREFIX);
    }

    /*conectar com o bot*/
    public void attachListener(Bot bot){
        bot.getJavaDiscordAPI().addEventListener(this);
    }


    /*registrar comando*/
    public void registerCommand(CommandBase command){
        commands.add(command);
    }

    public List<CommandBase> getCommands(){
        return commands;
    }
}
