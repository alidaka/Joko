package us.lidaka.joko;

import android.content.Context;
import android.gesture.Gesture;
import android.view.GestureDetector;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.LinearLayout;

/**
 * Created by augustus on 8/7/15.
 */
public class ListItemView extends LinearLayout {
    private ListItem mListItem;
    private GestureDetector mGestureDetector;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    public ListItemView(Context context, ListItem li) {
        super(context);

        mListItem = li;

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.list_item_view, this, true);

        CheckBox cb = (CheckBox)findViewById(R.id.list_item_checkbox);
        cb.setChecked(mListItem.getIsChecked());
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                setIsChecked(isChecked);
            }
        });

        mGestureDetector = new GestureDetector(context, new GestureListener());
        mGestureDetector.setIsLongpressEnabled(true);

        // TODO: this is wrong; I believe here I need a GestureHelper/GestureDetector
        /*
        setOnTouchListener(new OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                switch (event.getAction()) {
                    case MotionEvent.ACTION_DOWN:
                        //v.setAlpha(.5f);
                        return true;
                    case MotionEvent.ACTION_UP:
                        //v.setAlpha(1);
                        return true;
                    default:
                        break;
                }

                return false;
            }
        });
        */
    }

    private class GestureListener implements GestureDetector.OnGestureListener {
        @Override
        public boolean onDown(MotionEvent e) {
            return false;
        }

        @Override
        public void onShowPress(MotionEvent e) {

        }

        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            return false;
        }

        @Override
        public boolean onScroll(MotionEvent e1, MotionEvent e2, float distanceX, float distanceY) {
            return false;
        }

        @Override
        public void onLongPress(MotionEvent e) {

        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY) {
            return false;
        }
    }

    private void setIsChecked(boolean isChecked) {
        mListItem.setIsChecked(isChecked);

        // TODO: consider pulling this styling out
        setAlpha(isChecked ? .5f : 1);
    }

}
