package us.lidaka.joko;

import android.app.Activity;

import android.app.ActionBar;
import android.app.Fragment;
import android.app.FragmentManager;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.support.v4.widget.DrawerLayout;
import android.widget.ArrayAdapter;
import android.widget.CompoundButton;
import android.widget.ListView;
import android.widget.Switch;
import android.widget.Toast;

import java.util.ArrayList;
import java.util.List;


public class MainNotes extends Activity implements NavigationDrawerFragment.NavigationDrawerCallbacks, CompoundButton.OnCheckedChangeListener {

    /**
     * Fragment managing the behaviors, interactions and presentation of the navigation drawer.
     */
    private NavigationDrawerFragment mNavigationDrawerFragment;

    private PlaceholderFragment mFragment;

    /**
     * Used to store the last screen title. For use in {@link #restoreActionBar()}.
     */
    private CharSequence mTitle;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main_notes);

        mTitle = getTitle();

        // Set up the drawer.
        mNavigationDrawerFragment = (NavigationDrawerFragment) getFragmentManager().findFragmentById(R.id.navigation_drawer);
        mNavigationDrawerFragment.setUp(
                R.id.navigation_drawer,
                (DrawerLayout) findViewById(R.id.drawer_layout));
    }

    @Override
    public void onNavigationDrawerItemSelected(long id) {
        // TODO: translate position to id

        // update the main content by replacing fragments
        mFragment = PlaceholderFragment.newInstance(id);

        FragmentManager fragmentManager = getFragmentManager();
        fragmentManager.beginTransaction()
                .replace(R.id.container, mFragment)
                .commit();
    }

    public void onSectionAttached(String title) {
        mTitle = title;
    }

    public void restoreActionBar() {
        ActionBar actionBar = getActionBar();
        actionBar.setNavigationMode(ActionBar.NAVIGATION_MODE_STANDARD);
        actionBar.setDisplayShowTitleEnabled(true);
        actionBar.setTitle(mTitle);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        if (!mNavigationDrawerFragment.isDrawerOpen()) {
            // Only show items in the action bar relevant to this screen
            // if the drawer is not showing. Otherwise, let the drawer
            // decide what to show in the action bar.
            getMenuInflater().inflate(R.menu.main_notes, menu);

            View switchView = menu.findItem(R.id.action_toggleEdit).getActionView();
            Switch sw = (Switch)switchView.findViewById(R.id.edit_switch);
            sw.setOnCheckedChangeListener(this);

            restoreActionBar();

            return true;
        }
        return super.onCreateOptionsMenu(menu);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        switch (item.getItemId()) {
            case R.id.action_settings:
                Toast.makeText(this, "Settings...", Toast.LENGTH_SHORT).show();
                return true;

            case R.id.action_addItem:
                ListView lv = (ListView)((ViewGroup)findViewById(R.id.container)).getChildAt(0);
                ListAdapter adapter = (ListAdapter)lv.getAdapter();
                adapter.addItem();
                return true;

            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onCheckedChanged(CompoundButton buttonView, boolean isChecked) {
        ListView lv = (ListView)((ViewGroup)findViewById(R.id.container)).getChildAt(0);
        ListAdapter adapter = (ListAdapter)lv.getAdapter();
        adapter.toggleEdit(lv, isChecked);
    }

    /**
     * A placeholder fragment containing a simple view.
     */
    public static class PlaceholderFragment extends Fragment {
        /**
         * The fragment argument representing the section number for this
         * fragment.
         */
        private static final String ARG_SECTION_NUMBER = "section_number";

        /**
         * Returns a new instance of this fragment for the given section
         * number.
         */
        public static PlaceholderFragment newInstance(long id) {
            PlaceholderFragment fragment = new PlaceholderFragment();
            Bundle args = new Bundle();
            args.putLong(ARG_SECTION_NUMBER, id);
            fragment.setArguments(args);
            return fragment;
        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
            ListView view = (ListView) inflater.inflate(R.layout.fragment_main_notes, container, false);

            long listId = getArguments().getLong(ARG_SECTION_NUMBER);
            TitledOrderedList list = ListManager.getInstance().getList(listId);

            view.setAdapter(new ListAdapter(this.getActivity(), list));
            // TODO:?? view.setDescendantFocusability(ListView.FOCUS_AFTER_DESCENDANTS);

            return view;
        }

        @Override
        public void onAttach(Activity activity) {
            super.onAttach(activity);

            long listId = getArguments().getLong(ARG_SECTION_NUMBER);
            TitledOrderedList list = ListManager.getInstance().getList(listId);
            ((MainNotes) activity).onSectionAttached(list.getTitle());
        }
    }

}
