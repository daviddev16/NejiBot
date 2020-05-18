package nejidev.api.listeners;

import javax.annotation.Nullable;
import net.dv8tion.jda.api.entities.Message;

/*Evento quando alguma "Tag" for achada em uma mensagem.*/
public interface ITagEvent {

    void onTaggedMessageEvent(@Nullable Message message);

}
