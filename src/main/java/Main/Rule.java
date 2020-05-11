package Main;

import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.Role;

public class Rule {

    private String emoteName;
    private String roleId;

    public Rule(String emoteName, String roleId){
        this.setRoleId(roleId);
        this.setEmoteName(emoteName);
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

    public boolean isMine(Emote emote){
        return emote.getName().equals(getEmoteName());
    }

    public synchronized boolean hasTag(Member member, Guild guild){
        return member.getRoles().contains(guild.getRoleById(roleId));
    }

    public synchronized void addMember(Member member, Guild guild){
        if(!hasTag(member, guild)){
            guild.addRoleToMember(member, guild.getRoleById(getRoleId())).queue();
        }
        updateDisplayTag(member, guild);
    }

    public synchronized void removeMember(Member member, Guild guild){
        if(hasTag(member, guild)) {
            guild.removeRoleFromMember(member, guild.getRoleById(roleId)).queue();
        }
        updateDisplayTag(member, guild);
    }

    private synchronized  void updateDisplayTag(Member member, Guild guild){
        if(Manager.IsDeveloper(member, guild)) {
            guild.addRoleToMember(member, guild.getRoleById(Manager.DEVELOPER_TAG_ID)).queue();
            return;
        }
        guild.removeRoleFromMember(member, guild.getRoleById(Manager.DEVELOPER_TAG_ID)).queue();
    }
}
