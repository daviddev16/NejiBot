package nejidev.api.tag;

import net.dv8tion.jda.api.entities.Emote;

import java.util.ArrayList;
import java.util.List;

public class Tag {

    private String key;
    private List<Emote> emotes;

    public Tag(String key, List<Emote> emotes) {
        this.key = key;
        this.emotes = emotes;
    }

    public List<Emote> getEmotes() {
        return emotes;
    }

    public void setEmotes(List<Emote> emotes) {
        this.emotes = emotes;
    }

    public String getKey() {
        return key;
    }

    public void setKey(String key) {
        this.key = key;
    }

    public static Tag createTag(String key, Emote... emotes){
        List<Emote> emoteList = new ArrayList<>();
        for (Emote emote : emotes) {
            emoteList.add(emote);
        }
        return new Tag(key, emoteList);
    }
}
