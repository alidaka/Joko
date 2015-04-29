package us.lidaka.joko;

import android.content.Context;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.List;

// TODO: probably create a base class like TitledList and subtype numeral labeling, item completion action (check or delete), ...

public class TitledOrderedList implements Serializable {
    private String mTitle;
    private List<ListItem> mListItems;

    public TitledOrderedList(String title) {
        mTitle = title;
    }

    private void updateChanges(Context context) throws FileNotFoundException, IOException {
        FileOutputStream fos = context.getApplicationContext().openFileOutput(mTitle, Context.MODE_PRIVATE);
        ObjectOutputStream os = new ObjectOutputStream(fos);
        os.writeObject(this);
        os.close();
        fos.close();
    }

    /*
    private void loadFromFile(String filename) {
        FileInputStream fis = context.openFileInput(fileName);
        ObjectInputStream is = new ObjectInputStream(fis);
        SimpleClass simpleClass = (SimpleClass) is.readObject();
        is.close();
        fis.close();
    }
    */

    public void addItem(Context context, String text) throws IOException {
        addItem(context, new ListItem(text));
    }

    public void addItem(Context context, ListItem li) throws IOException {
        mListItems.add(li);
        updateChanges(context);
    }

    public String getTitle() {
        return mTitle;
    }
}
