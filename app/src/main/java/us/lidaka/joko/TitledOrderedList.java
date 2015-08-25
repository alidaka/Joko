package us.lidaka.joko;

import android.content.Context;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectOutputStream;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

// TODO: probably create a base class like TitledList and subtype numeral labeling, item completion action (check or delete), ...

public class TitledOrderedList implements Serializable {
    private String mTitle;
    private List<ListItem> mListItems;

    public TitledOrderedList(String title) {
        mTitle = title;
        mListItems = new ArrayList<ListItem>();
    }

    private void updateChangesAsync(Context context) {
        // TODO: implement an Executor which handles these better, rather than immediately spinning a dedicated thread
        final Context finalContext = context;

        Thread t = new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    updateChanges(finalContext);
                }
                catch (IOException e) {
                    throw new RuntimeException(e);
                }
            }
        });

        t.start();
    }

    private void updateChanges(Context context) throws IOException {
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

    public void addItem(Context context, String text) {
        addItem(context, new ListItem(text));
    }

    public void addItem(Context context, ListItem li) {
        mListItems.add(li);
        updateChangesAsync(context);
    }

    public String getTitle() {
        return mTitle;
    }

    public ListItem getItem(int position) {
        return mListItems.get(position);
    }

    public ListItem getItem(long id) {
        // TODO
        return null;
    }

    public int getCount() {
        return mListItems.size();
    }

    @Override
    public String toString() { return this.getTitle(); }
}
