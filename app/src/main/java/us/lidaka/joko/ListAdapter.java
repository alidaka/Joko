package us.lidaka.joko;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.ListView;
import android.widget.TextView;

/**
 * Created by augustus on 8/7/15.
 */
public class ListAdapter extends BaseAdapter {

    private TitledOrderedList mList;
    private Context mContext;
    private boolean mEditingEnabled;

    public ListAdapter(Context context, TitledOrderedList list) {
        mList = list;
        mContext = context;
        mEditingEnabled = false;
    }

    @Override
    public int getCount() {
        return mList.getCount();
    }

    @Override
    public Object getItem(int i) {
        return mList.getItem(i);
    }

    @Override
    public long getItemId(int i) {
        // ID is index for elements of a list
        return i;
    }

    @Override
    public View getView(int position, View convertView, ViewGroup parent) {
        ListItem li = mList.getItem(position);

        View view;
        if (convertView == null) {
            // Heuristic to check if this probably just got added and the intent is to edit
            boolean focusEdit = li.getText().isEmpty() && (position == (mList.getCount() - 1));
            view = new ListItemView(mContext, this, li, focusEdit);
        } else {
            view = convertView;
        }

        return view;
    }

    public void addItem() {
        mList.addItem(mContext, "");
        notifyDataSetChanged();
    }

    public void toggleEdit(ListView listView, boolean enableEdit) {
        // TODO: plumb this, and notify children
        mEditingEnabled = enableEdit;
    }

    public boolean getEditingEnabled() {
        return mEditingEnabled;
    }

}
