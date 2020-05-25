package nejidev.tags;

import nejidev.api.listeners.ITagEvent;
import nejidev.api.tag.TagQueryResult;
import net.dv8tion.jda.api.entities.Message;
import org.jetbrains.annotations.NotNull;

import javax.annotation.Nullable;
import java.util.Arrays;

public class OptionTagEvent implements ITagEvent {

    public void onTaggedMessageEvent(@Nullable Message message, @NotNull TagQueryResult tagQueryResult) {

        System.out.println(tagQueryResult.isValid());
        System.out.println(tagQueryResult.getKey());
        System.out.println(Arrays.toString(tagQueryResult.getOptions().toArray()));

    }
}
