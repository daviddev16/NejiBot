package nejidev.api;

import nejidev.api.utils.GuildUtils;
import nejidev.api.utils.Utils;
import nejidev.main.*;
import net.dv8tion.jda.api.entities.*;
import net.dv8tion.jda.api.events.message.react.MessageReactionAddEvent;
import net.dv8tion.jda.api.hooks.ListenerAdapter;

import javax.annotation.Nonnull;
import java.util.ArrayList;
import java.util.List;

import static java.util.Objects.*;

public abstract class Banner extends ListenerAdapter {

    /*id da mensagem do banner*/
    private long messageId;

    /*id do canal que o banner vai ficar*/
    private long textChannelId;

    private List<ReactionRole> reactionRoles;

    /*caminho do InputStream da foto do banner*/
    private String bannerPath;

    /*Guild do servidor*/
    private Guild guild;

    private long displayTagRoleId;

    /**
     * Esta classe permite você criar banners personalizados
     * em forma de mensagem que exibirá reações, e essas reações
     * irão servir para dar algum cargo a pessoa que reagiu.
    * */
    public Banner(long messageId){
        this.setMessageId(messageId);
        this.setReactionRoles(new ArrayList<>());
    }


    /*Caso ainda não exista a mensagem, use esta função para criar a mensagem personalizada*/
    public Banner displayBanner(){

        Bot bot = MainApplication.getBot();
        TextChannel channel = bot.getOfficialGuild().getTextChannelById(textChannelId);

        assert channel != null;
        channel.sendFile(requireNonNull(Utils.getInputStream(bannerPath)), "banner.png").queue(message -> {

            for(ReactionRole reactionRoles : getReactionRoles())
                message.addReaction(GuildUtils.findEmote(reactionRoles.getEmoteName(), bot)).queue();

        });

        return this;
    }

    /*Esta função será chamada quando o banner for adicionado ao bot*/
    public abstract boolean onAwake();

    /*Esta função será chamada quando o banner já estiver adicionado ao bot sem erros.*/
    public abstract void onAdded();


    @SuppressWarnings("ConstantConditions")
    public void onMessageReactionAdd(@Nonnull MessageReactionAddEvent event) {

        if (event.getUser().isBot()) {
            return;
        }
        if ((event.getChannel().getIdLong() == getTextChannelId()) &&
                event.getMessageId().equalsIgnoreCase(Long.toString(getMessageId()))) {

            ReactionRole foundRole = findReactionRole(event.getReactionEmote().getEmote());
            foundRole = requireNonNull(foundRole);
            addMemberToReactionRole(foundRole, event.getMember());
        }
    }

    /*limpar cargos do membro*/
    public void clearMember(Member member){

        Bot bot = MainApplication.getBot();
        TextChannel channel = bot.getOfficialGuild().getTextChannelById(textChannelId);

        assert channel != null;
        for(ReactionRole roles : getReactionRoles()){
            channel.removeReactionById(messageId, Utils.findEmoteByName(roles.getEmoteName()), member.getUser()).queue();
            roles.removeMember(member).queue();
        }
    }

    public ReactionRole findReactionRole(Emote emote){
        for(ReactionRole roles : getReactionRoles()){
            if(emote.getName().equalsIgnoreCase(roles.getEmoteName())){
                return roles;
            }
        }
        throw new NullPointerException("Could not found the ReactionRole.");
    }

    /*adicionar role por reação*/
    public void addReactionRoles(ReactionRole reactionRole){
        getReactionRoles().add(reactionRole);
    }

    public void setDisplayTagRoleId(long displayTagRoleId){
        this.displayTagRoleId = displayTagRoleId;
    }

    public List<ReactionRole> getReactionRoles(){
        return this.reactionRoles;
    }

    public long getMessageId() {
        return messageId;
    }

    public void setMessageId(long messageId) {
        this.messageId = messageId;
    }

    public long getTextChannelId() {
        return textChannelId;
    }

    public void setTextChannelId(long textChannelId) {
        this.textChannelId = textChannelId;
    }

    public void setReactionRoles(List<ReactionRole> reactionRoles) {
        this.reactionRoles = reactionRoles;
    }

    public String getBannerPath(){
        return bannerPath;
    }

    public void setBannerPath(String bannerPath){
        this.bannerPath = bannerPath;
    }

    /*adicionar role ao jogador*/
    private void addMemberToReactionRole(ReactionRole role, Member member){
        role.addMember(member).queue();
    }

    /*checar se o membro possui a tag*/
    private boolean hasAnyTag(Member member){
        for(ReactionRole role : getReactionRoles()) {
            if (role.hasTag(member)) {
                return true;
            }
        }
        return false;
    }

}
