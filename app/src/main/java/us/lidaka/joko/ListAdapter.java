package us.lidaka.joko;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.BaseAdapter;
import android.widget.TextView;

/**
 * Created by augustus on 8/7/15.
 */
public class ListAdapter extends BaseAdapter {

    private TitledOrderedList mList;

    private Context mContext;

    public ListAdapter(Context context, TitledOrderedList list) {
        mList = list;
        mContext = context;
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
            view = new ListItemView(mContext, li);
        } else {
            view = convertView;
        }

        TextView text = (TextView)view.findViewById(R.id.list_item_text);
        text.setText(li.getText());

        return view;
    }

}
