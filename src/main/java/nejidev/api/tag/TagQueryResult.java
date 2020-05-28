package nejidev.api.tag;

import net.dv8tion.jda.annotations.DeprecatedSince;

import java.util.List;

public class TagQueryResult {

    private final String key;

    private final List<String> options;

    private final boolean isValid;

    public TagQueryResult(String key, List<String> options, boolean isValid) {
        this.key = key;
        this.options = options;
        this.isValid = isValid;
    }

    public String getKey() {
        return key;
    }

    public List<String> getOptions() {
        return options;
    }

    /*this variable will always be true on ITagEvent TagQueryResult.*/
    @DeprecatedSince("TagQueryResult")
    public boolean isValid() {
        return isValid;
    }
}
