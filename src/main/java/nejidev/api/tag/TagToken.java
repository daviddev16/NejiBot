package nejidev.api.tag;

import net.dv8tion.jda.api.entities.Message;
import org.apache.commons.lang3.StringUtils;

import java.util.ArrayList;
import java.util.List;

/*classe responsavel por procurar alguma tag na mensagem do membro*/
final class TagToken {

    /*
     *checar se a tag foi colocada corretamente e se pode ser considerada uma tag ou
     *somente uma mensagem
    */
    private boolean isValid;

    /*StringBuffer para concatenar os characteres dentro da tag*/
    private final StringBuffer buffer;

    /*Token options*/
    private final List<String> options;

    public TagToken(){
        this.isValid = false;
        this.buffer = new StringBuffer();
        options = new ArrayList<>();
    }

    public boolean isValid(){
        return isValid;
    }

    public TagToken setValid(boolean v){
        this.isValid = v;
        return this;
    }

    public void addOption(String option){
        options.add(option);
    }

    public void append(char char1){
        buffer.append(char1);
    }

    /*obter resultado da pesquisa*/
    public String getResultKey(){
        return buffer.toString().trim();
    }

    public List<String> getOptions(){
        return this.options;
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
    public static void compile(List<Tag> registeredTags, Message message){

        List<TagToken> tokenList = new ArrayList<>();

        char currentChar;
        int currentIndex = 0;

        boolean optionToken = false;
        TagToken currentToken = null;

        StringBuilder optionBuffer = new StringBuilder();

        while(currentIndex < message.getContentRaw().length()){

            currentChar = message.getContentRaw().charAt(currentIndex);

            if(currentChar == '[' && currentToken == null){

                currentToken = new TagToken();
                currentToken.setValid(true);

                currentIndex++;
                continue;

            }
            else if(currentChar == ']' && currentToken != null){

                if(StringUtils.isNotEmpty(optionBuffer.toString())){

                    optionBuffer.trimToSize();

                    currentToken.addOption(optionBuffer.toString());

                }

                if(currentToken.getResultKey().isEmpty()){
                    currentToken.setValid(false);
                }

                tokenList.add(currentToken);

                currentToken = null;

                currentIndex++;
                continue;

            }
            else if(currentChar == ':' && !optionToken) {
                optionToken = true;
                currentIndex++;
                continue;
            }
            else if(currentToken != null && !optionToken) {
                currentToken.append(currentChar);
            }
            else if(currentChar == ',' && optionToken && currentToken != null){

                if(StringUtils.isNotEmpty(optionBuffer.toString())){

                    optionBuffer.trimToSize();

                    currentToken.addOption(optionBuffer.toString());

                }

                optionBuffer.delete(0, optionBuffer.length());

                currentIndex++;
                continue;
            }
            else if(optionToken){
                optionBuffer.append(currentChar);
            }
            currentIndex++;
        }

        try {

            assert currentToken != null;
            tokenList.stream().filter(TagToken::isValid).forEach(tagToken -> {

                registeredTags.stream().filter(tag -> tag.isMine(tagToken)).forEach(foundTag -> {

                    foundTag.callEvent(message, new TagQueryResult(tagToken.getResultKey(), tagToken.getOptions(), tagToken.isValid()));

                });
            });

        }catch (Exception e){
            e.printStackTrace();
        }
    }

}
