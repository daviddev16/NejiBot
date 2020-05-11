package Main;

import net.dv8tion.jda.api.JDABuilder;
import net.dv8tion.jda.api.entities.Emote;
import net.dv8tion.jda.api.entities.Guild;
import net.dv8tion.jda.api.entities.Member;
import net.dv8tion.jda.api.entities.TextChannel;

import java.io.InputStream;
import java.util.ArrayList;
import java.util.List;

public class Manager {

    public static final String BOT_TOKEN = "NzA3Nzg2MzUzOTQwNjI3NDY2.XrbuMw.Zo0GwJsQnzxKGrdD7bwR1lnd6_k";

    public static long PROGRAMMING_LANGUAGE_MESSAGE_ID = 709263002599161987L;

    public static final long REGISTER_TEXT_CHANNEL_ID = 708748306049663067L;

    public static final long DEVELOPER_TAG_ID = 707784153252495424L;

    private volatile List<Rule> plRules;

    public static Rule CSHARP = new Rule("CSharp", "707782080377126962");
    public static Rule CPP = new Rule("CPP", "707782500012916776");
    public static Rule JAVASCRIPT = new Rule("JavaScript", "707782421055275018");
    public static Rule PYTHON = new Rule("Python", "707782275533897778");
    public static Rule JAVA = new Rule("Java", "707782228608024627");
    public static Rule RUBY = new Rule("Ruby", "707783759054897184");

    public static Manager singleton;

    public Manager(){
        singleton = this;
    }
    /*setup bot*/
    public void setup(JDABuilder builder) {
        plRules = new ArrayList<>();
        plRules.add(CSHARP);
        plRules.add(CPP);
        plRules.add(JAVASCRIPT);
        plRules.add(PYTHON);
        plRules.add(JAVA);
        plRules.add(RUBY);
    }

    public List<Rule> getRules(){
        return plRules;
    }

    /*find registration message*/
    public boolean catchRegistrationMessage(TextChannel channel, long messageId){
        return channel.getIdLong() == Manager.REGISTER_TEXT_CHANNEL_ID &&
                messageId == Manager.PROGRAMMING_LANGUAGE_MESSAGE_ID;
    }

    /*check if member is developer*/
    public boolean IsDeveloper(Member member, Guild guild) {
        for (Rule allLanguages : plRules) {
            if (allLanguages.hasTag(member, guild)) {
                return true;
            }
        }
        return false;
    }

    /*find emote by name*/
    public Emote getEmoteByName(String name, Guild guild) {
        for(Emote emote : guild.getEmotes()){
            if(emote.getName().equalsIgnoreCase(name)){
                return emote;
            }
        }
        return null;
    }

    /*find rule by emote*/
    public Rule findMyRuleByEmote(Emote emote) {
        for (Rule allLanguages : plRules) {
            if(allLanguages.isMine(emote)){
                return allLanguages;
            }
        }
        return null;
    }

    public static InputStream getInputStream(String name){
        return Manager.class.getResourceAsStream(name);
    }

    public static Manager getInstance(){
        return singleton;
    }

}
