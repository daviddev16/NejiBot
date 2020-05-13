package nejidev.api;

import nejidev.main.MainApplication;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

import java.util.Objects;

public class ReactionRole {

    private String emoteName;
    private String roleId;

    private long roleDisplayId;

    public ReactionRole(String emoteName, String roleId){
        this.setRoleId(roleId);
        this.setEmoteName(emoteName);
    }

    public ReactionRole setRoleDisplayId(long roleDisplayId){
        this.roleDisplayId = roleDisplayId;
        return this;
    }

    public long getRoleDisplayId(){
        return roleDisplayId;
    }

    public String getEmoteName(){
        return this.emoteName;
    }

    public void setEmoteName(String emoteName){
        this.emoteName = emoteName;
    }

    public String getRoleId() {
        return roleId;
    }

    public void setRoleId(String roleId) {
        this.roleId = roleId;
    }

    public synchronized boolean isMine(Emote emote){
        return emote.getName().equals(getEmoteName());
    }

    public synchronized boolean hasTag(Member member){

        if(member.getRoles().isEmpty()){
            return false;
        }

        for(Role roles : member.getRoles()) {
            if (roles.getId().equalsIgnoreCase(roleId)) {
                return true;
            }
        }
        return false;
    }


    public synchronized void addMember(Member member){
        if(!hasTag(member)){
            MainApplication.getBot().getOfficialGuild().addRoleToMember(member, Objects.requireNonNull(MainApplication.getBot().getOfficialGuild().getRoleById(getRoleId()))).queue();
        }
    }

    public synchronized void removeMember(Member member){
        if(hasTag(member)) {
            MainApplication.getBot().getOfficialGuild().removeRoleFromMember(member, Objects.requireNonNull(MainApplication.getBot().getOfficialGuild().getRoleById(roleId))).queue();
        }
    }
}
