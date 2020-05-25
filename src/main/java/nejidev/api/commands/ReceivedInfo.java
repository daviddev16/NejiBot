package nejidev.api.commands;

import nejidev.api.utils.Utils;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;


public class ReceivedInfo {

    /*comando executado*/
    private final String command;

    /*conteudo inteiro da mensagem*/
    private final String contentRaw;

    /**
     * lista de parametros pasados pelo comando.
     *
     * Exemplo:
     * !test a b c @User#1429
     *
     * Output: [a, b, c, @<id_do_user>]
     *
     * */
    private final String[] arguments;

    /*lista de membros mencionados na mensagem.*/
    private List<Member> mentions;

    /*membro que enviou o comando*/
    private Member sender;

    /*evento registrado*/
    private MessageReceivedEvent event;

    /*Classe responsavel por enviar as informações do comando dado pelo Membro na mensagem. */
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

        if(event.getMember().getUser().isBot()) return;

        String contentRaw = event.getMessage().getContentRaw().trim();

        /*verificar se existe um comando na string*/
        if (contentRaw.startsWith(CommandBase.COMMAND_PREFIX)) {

            /*remover prefixdo da string*/
            contentRaw = contentRaw.substring(CommandBase.COMMAND_PREFIX.length(), event.getMessage().getContentRaw().length());

            /*dividir a string por parametro*/
            String[] arguments = StringUtils.split(contentRaw);
            String command = arguments[0];

            /*remover index do nome do comando*/
            arguments = Utils.removeIndex(arguments, 0);

            ReceivedInfo receivedInfo = new ReceivedInfo(command, contentRaw, arguments);

            receivedInfo.setSender(event.getMember());
            receivedInfo.setReceivedEvent(event);

            receivedInfo.setMentions(event.getMessage().getMentionedMembers());

            /*executar comando*/
            receivedInfoConsumer.accept(receivedInfo);
        }
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

    private void setMentions(List<Member> mentions) {
        this.mentions = mentions;
    }

    private void setReceivedEvent(MessageReceivedEvent event){
        this.event = event;
    }

    public MessageReceivedEvent getReceiverEvent(){
        return event;
    }

    private void setSender(Member member){
        this.sender = member;
    }

    public Member getSender(){
        return sender;
    }
}


