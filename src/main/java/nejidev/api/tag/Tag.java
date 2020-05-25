package nejidev.api.tag;

import nejidev.api.listeners.ITagEvent;
import nejidev.tags.EmptyTagEvent;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;

import java.util.function.Supplier;

/*Classe responsavel por criar uma tag e servir como base para leitura*/
public class Tag {

    /** chave para detec��o da tag, por exemplo
     *
     * key = "feedback"
     *
     * Oque o compilador vai achar na mensagem = "... [feedback]...",
     * ele retornar� caso achada o valor o objeto "tag"
     *
    * */
    private final String key;

    /*
    * evento que acontecer� quando a tag for compilada,
    * */
    private final ITagEvent event;

    public Tag(String key, ITagEvent event) {
        this.key = key;
        this.event = event;
    }

    public String getKey() {
        return key;
    }

    /*quando tudo for compilado e a tag for validada
    * essa fun��o ser� chamada*/
    public void callEvent(@NotNull Message message, @NotNull TagQueryResult queryResult) { event.onTaggedMessageEvent(message, queryResult); }

    /*metodo para criar um objeto tag com mais facilidade. */
    public static Tag createTag(String key, Supplier<ITagEvent> event){
        ITagEvent tagEvent = event.get();
        if(tagEvent == null){
            tagEvent = new EmptyTagEvent();
        }
        return new Tag(key, tagEvent);
    }

    public boolean isMine(TagToken token){
        return getKey().equalsIgnoreCase(token.getResultKey());
    }
}
