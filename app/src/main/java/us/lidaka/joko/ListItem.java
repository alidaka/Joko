package us.lidaka.joko;

import java.io.Serializable;

/**
 * Created by alidaka on 4/27/15.
 */
public class ListItem implements Serializable {
    private String mText;

    public ListItem(String text) {
        mText = text;
    }
}
