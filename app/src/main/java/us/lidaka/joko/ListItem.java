package us.lidaka.joko;

import java.io.Serializable;

/**
 * Created by alidaka on 4/27/15.
 */
public class ListItem implements Serializable {
    private String mText;
    private boolean mIsChecked;

    public ListItem(String text) {
        mText = text;
        mIsChecked = false;
    }

    public String getText() {
        return mText;
    }

    public void setText(String text) {
        mText = text;
    }

    public boolean getIsChecked() {
        return mIsChecked;
    }

    public void setIsChecked(boolean isChecked) {
        mIsChecked = isChecked;
    }

    @Override
    public String toString() {
        return this.getText();
    }
}
