package us.lidaka.joko;

import android.content.Context;
import android.gesture.Gesture;
import android.graphics.drawable.Drawable;
import android.text.Editable;
import android.text.InputType;
import android.text.TextWatcher;
import android.view.GestureDetector;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.MotionEvent;
import android.view.View;
import android.view.inputmethod.EditorInfo;
import android.widget.CheckBox;
import android.widget.CompoundButton;
import android.widget.EditText;
import android.widget.LinearLayout;
import android.widget.TextView;

/**
 * Created by augustus on 8/7/15.
 */
public class ListItemView extends LinearLayout implements TextView.OnFocusChangeListener, TextView.OnEditorActionListener {
    private ListItem mListItem;
    private GestureDetector mGestureDetector;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    public ListItemView(Context context, ListItem li, boolean focusEdit) {
        super(context);

        mListItem = li;

        LayoutInflater inflater = (LayoutInflater)context.getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        inflater.inflate(R.layout.list_item_view, this, true);

        CheckBox cb = (CheckBox)findViewById(R.id.list_item_checkbox);
        cb.setChecked(mListItem.getIsChecked());
        setIsChecked(mListItem.getIsChecked());
        cb.setOnCheckedChangeListener(new CompoundButton.OnCheckedChangeListener() {
            @Override
            public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
                mListItem.setIsChecked(isChecked);
                setIsChecked(isChecked);
            }
        });

        EditText et = (EditText)findViewById(R.id.list_item_text);
        et.setText(mListItem.getText());
        // another option, use setInputType??
        if (!focusEdit) {
            et.setFocusable(false); // TODO: toggle based on mode
        }
        else {
            et.setFocusable(true);
            et.requestFocus();
        }

        et.setOnEditorActionListener(this);
        et.setOnFocusChangeListener(this);

        mGestureDetector = new GestureDetector(context, new GestureListener());
        mGestureDetector.setIsLongpressEnabled(true);
    }

    private void updateText(TextView v) {
        String str = v.getText().toString();
        mListItem.setText(str);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        //TODO: is this right? Do we need to check other keys? Should it be on KeyEvent.ACTION_UP instead??
        //if (actionId == EditorInfo.IME_NULL && event.getAction() == KeyEvent.ACTION_DOWN) {
        if (((actionId == EditorInfo.IME_ACTION_DONE) || (actionId == EditorInfo.IME_NULL)) && (event.getAction() == KeyEvent.ACTION_UP)) {
            updateText(v);
        }

        return true;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        if (!hasFocus) {
            updateText((TextView)v);
        }
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
        // TODO: consider pulling this styling out
        setAlpha(isChecked ? .5f : 1);
    }

}
