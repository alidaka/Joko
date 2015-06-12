package us.lidaka.joko;

import android.app.Application;
import android.content.Context;

import java.util.ArrayList;

/**
 * Created by augustus on 5/28/15.
 */
public class ListManager {
    private static ListManager ourInstance = new ListManager();

    public static ListManager getInstance() {
        return ourInstance;
    }

    public TitledOrderedList[] getLists() {
        return mLists.toArray(new TitledOrderedList[mLists.size()]);
    }

    public String[] getTitles() {
        String[] titles = new String[mLists.size()];
        for (int i = 0; i < mLists.size(); i++) {
            titles[i] = mLists.get(i).getTitle();
        }

        return titles;
    }

    public TitledOrderedList getList(long id) {
        // TODO: hack: give them real IDs, not just indices
        if (id >= mLists.size()) {
            throw new IllegalArgumentException("invalid list ID");
        }

        return mLists.get((int)id);
    }

    private ArrayList<TitledOrderedList> mLists;

    private ListManager() {
        // TODO: load pre-existing lists here(?)
        {
            mLists = new ArrayList<TitledOrderedList>();
            mLists.add(new TitledOrderedList("first list"));
            mLists.add(new TitledOrderedList("second list"));
            mLists.add(new TitledOrderedList("third list"));

            for (int i = 4; i < 50; i++) {
                mLists.add(new TitledOrderedList(String.format("list %d", i)));
            }

            Context context = JokoApp.getContext();
            try {
                mLists.get(0).addItem(context, "first item!");
                mLists.get(0).addItem(context, "01");
                mLists.get(0).addItem(context, "02");
                mLists.get(1).addItem(context, "second list");
                mLists.get(1).addItem(context, "11");
                mLists.get(1).addItem(context, "12");
                mLists.get(1).addItem(context, "13");
                mLists.get(2).addItem(context, "list 3");
            }
            catch(Exception e){}
        }

    }
}
