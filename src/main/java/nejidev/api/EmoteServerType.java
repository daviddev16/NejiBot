package nejidev.api;

import org.jetbrains.annotations.Contract;

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
    HOUSE_BRILIANCE("briliance");

    private String name;

    @Contract(pure = true)
    EmoteServerType(String name){
        this.name = name;
    }

    public String getEmoteName(){
        return name;
    }
}
