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

    public static List<Rule> programmingLanguageRules;

    public static Rule CSHARP = new Rule("CSharp", "707782080377126962");
    public static Rule CPP = new Rule("CPP", "707782500012916776");
    public static Rule JAVASCRIPT = new Rule("JavaScript", "707782421055275018");
    public static Rule PYTHON = new Rule("Python", "707782275533897778");
    public static Rule JAVA = new Rule("Java", "707782228608024627");
    public static Rule RUBY = new Rule("Ruby", "707783759054897184");

    public static void setup(JDABuilder builder) {

        programmingLanguageRules = new ArrayList<Rule>();
        programmingLanguageRules.add(CSHARP);
        programmingLanguageRules.add(CPP);
        programmingLanguageRules.add(JAVASCRIPT);
        programmingLanguageRules.add(PYTHON);
        programmingLanguageRules.add(JAVA);
        programmingLanguageRules.add(RUBY);

    }

    public static InputStream getInputStream(String name){
        return Manager.class.getResourceAsStream(name);
    }

    public static boolean catchRegistrationMessage(TextChannel channel, long messageId){
        if(channel.getIdLong() == Manager.REGISTER_TEXT_CHANNEL_ID &&
                messageId == Manager.PROGRAMMING_LANGUAGE_MESSAGE_ID){
            return true;
        }
        return false;
    }

    public static boolean IsDeveloper(Member member, Guild guild) {
        for (Rule allLanguages : programmingLanguageRules) {
            if (allLanguages.hasTag(member, guild)) {
                return true;
            }
        }
        return false;
    }

    public static Emote getEmoteByName(String name, Guild guild) {
        for(Emote emote : guild.getEmotes()){
            if(emote.getName().equalsIgnoreCase(name)){
                return emote;
            }
        }
        return null;
    }

    public static Rule findMyRuleByEmote(Emote emote) {
        for (Rule allLanguages : programmingLanguageRules) {
            if(allLanguages.isMine(emote)){
                return allLanguages;
            }
        }
        return null;
    }

}
