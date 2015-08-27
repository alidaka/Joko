package us.lidaka.joko;

import android.app.Activity;
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
import android.view.WindowManager;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
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
    private ListAdapter mParentAdapter;
    private boolean mEditingEnabled;

    @Override
    public boolean onTouchEvent(MotionEvent event) {
        return mGestureDetector.onTouchEvent(event);
    }

    public ListItemView(Context context, ListAdapter parentAdapter, ListItem li, boolean focusEdit) {
        super(context);

        mListItem = li;
        mParentAdapter = parentAdapter;

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

        et.setOnEditorActionListener(this);
        et.setOnFocusChangeListener(this);

        if (!focusEdit) {
            // This object represents a pre-existing element (i.e., user did not just hit "add a new item")
            if (mParentAdapter.getEditingEnabled()) {
                enableEditing(et, false);
            }
            else {
                disableEditing(et);
            }
        }
        else {
            // This object is new
            enableEditing(et, true);
        }

        mGestureDetector = new GestureDetector(context, new GestureListener());
        mGestureDetector.setIsLongpressEnabled(true);
    }

    private void disableEditing(TextView view) {
        view.setInputType(InputType.TYPE_NULL); // TODO: toggle based on mode
        mEditingEnabled = false;
    }

    private void enableEditing(TextView view, boolean requestFocus) {
        view.setInputType(InputType.TYPE_TEXT_FLAG_CAP_SENTENCES); // TODO: toggle based on mode
        mEditingEnabled = true;

        if (requestFocus) {
            view.requestFocus();
        }
    }

    private void onTextSubmission(TextView v) {
        String str = v.getText().toString();
        mListItem.setText(str);

        disableKeyboard(v);

        if (!mParentAdapter.getEditingEnabled()) {
            // User just submitted a new element but we're not in edit mode
            disableEditing(v);
        }
    }

    private void enableKeyboard(View view) {
        if (false) {
            InputMethodManager imm = (InputMethodManager) view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
            imm.showSoftInput(view, InputMethodManager.SHOW_IMPLICIT);
        }
        else {
            ((Activity)getContext()).getWindow().setSoftInputMode(WindowManager.LayoutParams.SOFT_INPUT_STATE_ALWAYS_VISIBLE);
        }
        //imm.toggleSoftInputFromWindow(view.getWindowToken(), InputMethodManager.SHOW_IMPLICIT, 0);
    }

    private void disableKeyboard(View view) {
        InputMethodManager imm = (InputMethodManager)view.getContext().getSystemService(Context.INPUT_METHOD_SERVICE);
        imm.hideSoftInputFromWindow(view.getWindowToken(), InputMethodManager.HIDE_IMPLICIT_ONLY);
    }

    @Override
    public boolean onEditorAction(TextView v, int actionId, KeyEvent event) {
        boolean actionSubmit = (actionId == EditorInfo.IME_ACTION_DONE) || (actionId == EditorInfo.IME_NULL);
        boolean keyEventSubmit = (event == null) || (event.getAction() == KeyEvent.ACTION_UP);
        if (actionSubmit && keyEventSubmit) {
            onTextSubmission(v);
        }

        return true;
    }

    @Override
    public void onFocusChange(View v, boolean hasFocus) {
        // If focus is changing but editing is not enabled, ignore
        if (mEditingEnabled) {
            if (!hasFocus) {
                onTextSubmission((TextView)v);
            } else {
                enableKeyboard(v);
            }
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
