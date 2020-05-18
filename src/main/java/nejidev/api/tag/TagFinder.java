package nejidev.api.tag;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.events.EventException;
import java.util.function.Supplier;

/*classe responsavel por procurar alguma tag na mensagem do membro*/
final class TagFinder {

    /*
     *checar se a tag foi colocada corretamente e se pode ser considerada uma tag ou
     *somente uma mensagem
    */
    private boolean isValid;

    /*indicar se uma tag foi aberta ou fechada*/
    private boolean isClosed;

    /*StringBuffer para concatenar os characteres dentro da tag*/
    private StringBuffer buffer;

    public TagFinder(){
        this.isValid = false;
        this.isClosed = true;
        this.buffer = new StringBuffer();
    }

    public boolean isValid(){
        return isValid;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public void setClosed(boolean c){
        this.isClosed = c;
    }

    public TagFinder setValid(boolean v){
        this.isValid = v;
        return this;
    }

    public StringBuffer getBuffer(){
        return buffer;
    }

    /*obter resultado da pesquisa*/
    public String getResultKey(){
        return buffer.toString().trim();
    }

    /*
    * verificar se na mensagem contem os caracteres fundamentais
    * para uma tag ser achada
    * */
    public static boolean containsTag(Message message){
        return (message.getContentRaw().contains("[") && message.getContentRaw().contains("]"));
    }

    /*
    * compilador serve para procurar a primeira tag que for achada na mensagem do membro,
    * ler caracter por caracter em busca dos caracteres fundamentais para a tag existir
    * ,assim que achados, ele cria um "TagFinder" e preenche com as informações que foi
    * recebida pela estrutura de repetição, dando os seguintes resultados:
    *
    * 1 - se a tag é valida
    * 2 - se está fechada ou aberta
    * 3 - o resultado em String da tag.
    *
    * */
    public static void compile(TagManager tagManager, MessageReceivedEvent event, Supplier<TagFinder> finderSupplier, Message message){

        /*new tagFinder do evento*/
        TagFinder tagFinder = finderSupplier.get();
        tagFinder.setValid(false).setClosed(true);

        char currentChar;
        int currentIndex = 0;

        String contentRaw = event.getMessage().getContentRaw().trim();

        while(currentIndex < contentRaw.length()) {

            currentChar = contentRaw.charAt(currentIndex);
            if(currentChar == '[' && tagFinder.isClosed()){
                tagFinder.setClosed(false);
            }

            else if(currentChar == ']' && !tagFinder.isClosed()) {
                tagFinder.setClosed(true);
                if(StringUtils.isNotBlank(tagFinder.getBuffer().toString())){
                    tagFinder.setValid(true);
                }
                break;
            }

            else if(!tagFinder.isClosed()){
                tagFinder.getBuffer().append(currentChar);
            }

            currentIndex++;

        }

        if(!tagFinder.isValid()){
            return;
        }
        try {

            /*procurar e chamar o evento apos toda a validação da tag.*/
            tagManager.getTags()
                    .stream()
                    .filter(tag1 -> tag1.getKey().equalsIgnoreCase(tagFinder.getResultKey()))
                    .findFirst()
                    .orElseThrow(() -> new EventException((short)-1, "Não foi possivel localizar a tag."))
                    .callEvent(message);
        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
