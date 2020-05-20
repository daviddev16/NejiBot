package nejidev.api.listeners;

import net.dv8tion.jda.api.EmbedBuilder;
import net.dv8tion.jda.api.entities.MessageEmbed;
import net.dv8tion.jda.api.requests.restaction.MessageAction;

public interface IOutput<E> {

    void sendSuccess(E e, String title, String hexadecimalColor, String message);

    MessageAction sendEmbed(E e, EmbedBuilder embed);

    MessageAction sendSimple(E e, String simpleMessage);

    void sendError(E e, MessageEmbed.Field usage);


}
