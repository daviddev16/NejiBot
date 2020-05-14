package nejidev.api.commands;

import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

/*Classe responsavel por enviar as informações do comando dado pelo Membro na mensagem. */
public class ReceivedInfo {

    private String command;

    private String contentRaw;

    private String[] arguments;

    private List<Member> mentions;

    private Member sender;

    private MessageReceivedEvent event;

    public ReceivedInfo(String command, String contentRaw, String[] arguments){
        /*nome apos prefixo*/
        this.command = command;

        /*conteudo da mensagem*/
        this.contentRaw = contentRaw;

        /*parametros do comando*/
        this.arguments = arguments;

        /*menções na mensagem*/
        this.mentions = new ArrayList<>();
    }

    /*esta função analisa a String do conteudo da mensagem e cria o ReceivedInfo, apos isso ele enviara ao executor do comando.
    *O comando será verificado se bate com o enviado na mensagem antes de processar a query.*/
    public static void query(MessageReceivedEvent event, Consumer<ReceivedInfo> receivedInfoConsumer) {

        String contentRaw = event.getMessage().getContentRaw().trim();

        /*verificar se existe um comando na string*/
        if (contentRaw.startsWith(CommandBase.COMMAND_PREFIX)) {

            /*remover prefixdo da string*/
            contentRaw = contentRaw.substring(CommandBase.COMMAND_PREFIX.length(), event.getMessage().getContentRaw().length());

            /*dividir a string por parametro*/
            String[] arguments = StringUtils.split(contentRaw);
            String command = arguments[0];

            /*remover index do nome do comando*/
            arguments = removeIndex(arguments, 0);

            ReceivedInfo receivedInfo = new ReceivedInfo(command, contentRaw, arguments);

            receivedInfo.setSender(event.getMember());
            receivedInfo.setReceivedEvent(event);

            receivedInfo.setMentions(event.getMessage().getMentionedMembers());

            /*executar comando*/
            receivedInfoConsumer.accept(receivedInfo);
        }
    }

    /*remover indice de uma array*/
    private static String[] removeIndex(String[] arr,  int index) {
        if (arr == null || index < 0 || index >= arr.length) {
            return arr;
        }
        String[] anotherArray = new String[arr.length - 1];
        for (int i = 0, k = 0; i < arr.length; i++) {
            if (i == index) {
                continue;
            }
            anotherArray[k++] = arr[i];
        }
        return anotherArray;
    }

    public String getCommand() {
        return command;
    }

    public String getContentRaw() {
        return contentRaw;
    }

    public String[] getArguments() {
        return arguments;
    }

    public List<Member> getMentions() {
        return mentions;
    }

    public void setMentions(List<Member> mentions) {
        this.mentions = mentions;
    }

    public void setReceivedEvent(MessageReceivedEvent event){
        this.event = event;
    }

    public MessageReceivedEvent getReceiverEvent(){
        return event;
    }

    public void setSender(Member member){
        this.sender = member;
    }

    public Member getSender(){
        return sender;
    }
}


