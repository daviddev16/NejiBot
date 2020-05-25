package nejidev.api.listeners;

import nejidev.api.tag.TagQueryResult;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;

/*Evento quando alguma "Tag" for achada em uma mensagem.*/
public interface ITagEvent {

    void onTaggedMessageEvent(@Nullable Message message, @NotNull TagQueryResult tagQueryResult);

}
