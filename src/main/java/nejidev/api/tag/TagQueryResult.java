package nejidev.api.tag;

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
    @Deprecated
    public boolean isValid() {
        return isValid;
    }
}
