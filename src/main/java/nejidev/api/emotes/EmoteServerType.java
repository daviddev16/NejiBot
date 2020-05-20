package nejidev.api.emotes;

import org.jetbrains.annotations.Contract;

/*enum com todos os emotes usados pelo bot para gerar reação*/
public enum EmoteServerType {

    /*custom debug emotes*/
    OK("ok"),
    WARNING("warning"),
    DENIED("denied"),

    /*feedback emotes*/
    YES("yes"),
    NO("no"),

    /*discord house emotes*/
    HOUSE_BRAVERY("bravery"),
    HOUSE_BALANCE("balance"),
    HOUSE_BRILIANCE("briliance"),

    /*somethings*/
    SICKO("sick"),

    /*neji emotes*/
    HAPPY("happy"),

    /*issue tag emotes*/
    CLOSED("closed"),
    OPENED("open"),

    /*voting emotes*/
    UP_VOTE("upvote"),
    DOWN_VOTE("downvote");


    /*nome do emote na lista de emotes do server*/
    private final String name;

    @Contract(pure = true)
    EmoteServerType(String name){
        this.name = name;
    }

    public String getEmoteName(){
        return name;
    }
}
