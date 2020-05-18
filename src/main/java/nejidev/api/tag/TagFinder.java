package nejidev.api.tag;

import net.dv8tion.jda.api.entities.Message;
import net.dv8tion.jda.api.events.message.MessageReceivedEvent;
import org.apache.commons.lang3.StringUtils;
import org.w3c.dom.events.EventException;

import java.util.function.Supplier;

class TagFinder {

    private boolean isValid;
    private boolean isClosed;
    private StringBuffer buffer;

    public TagFinder(){
        this.isValid = false;
        this.isClosed = true;
        this.buffer = new StringBuffer();
    }

    public boolean isValid(){
        return isValid;
    }

    public boolean isClosed() {
        return isClosed;
    }

    public TagFinder setClosed(boolean c){
        this.isClosed = c;
        return this;
    }

    public TagFinder setValid(boolean v){
        this.isValid = v;
        return this;
    }

    public StringBuffer getBuffer(){
        return buffer;
    }

    public String getResultKey(){
        return buffer.toString().trim();
    }

    public static boolean containsTag(Message message){
        return (message.getContentRaw().contains("[") && message.getContentRaw().contains("]"));
    }

    public static void compile(TagManager tagManager, MessageReceivedEvent event, Supplier<TagFinder> finderSupplier, Message message){
        TagFinder tagFinder = finderSupplier.get();
        tagFinder.setValid(false).setClosed(true);
        char currentChar;
        int currentIndex = 0;
        String contentRaw = event.getMessage().getContentRaw().trim();
        while(currentIndex < contentRaw.length()) {
            currentChar = contentRaw.charAt(currentIndex);
            if(currentChar == '[' && tagFinder.isClosed()){
                tagFinder.setClosed(false);
            }
            else if(currentChar == ']' && !tagFinder.isClosed()) {
                tagFinder.setClosed(true);
                if(StringUtils.isNotBlank(tagFinder.getBuffer().toString())){
                    tagFinder.setValid(true);
                }
                break;
            }
            else if(!tagFinder.isClosed()){
                tagFinder.getBuffer().append(currentChar);
            }
            currentIndex++;
        }
        if(tagFinder.isValid()){
            return;
        }
        try {
            tagManager.getTags()
                    .stream()
                    .filter(tag1 -> tag1.getKey().equals(tagFinder.getResultKey()))
                    .findFirst()
                    .orElseThrow(() -> new EventException((short)-1, "Não foi possivel localizar a tag."))
                    .getEmotes()
                    .forEach(emote -> {
                        message.addReaction(emote).queue();
                    });
        }catch (Exception e){
            e.printStackTrace();
        }
        return;
    }

}
