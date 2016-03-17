package com.scu.smartgrocerytracker.Notes;

import android.app.ListFragment;
import android.content.Intent;
import android.database.Cursor;
import android.os.Bundle;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;
import android.widget.Button;
import android.widget.ListView;
import android.widget.SimpleCursorAdapter;

import com.scu.smartgrocerytracker.R;
import com.scu.smartgrocerytracker.SmartGroceryDBHelper;

/**
 * Created by namrataojha on 3/16/16.
 */
public class NoteList extends ListFragment {

    //private static final int ACTIVITY_CREATE=0;
    private static final int ACTIVITY_EDIT=1;

    private static final int DELETE_ID = Menu.FIRST;
    private int mNoteNumber = 1;

    private SmartGroceryDBHelper mDbHelper;
    ViewGroup rootView;
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
         rootView = (ViewGroup) inflater.inflate(R.layout.notelist, container, false);
        //Uninstall the app {
        return rootView;
    }

    @Override
    public void onViewCreated (View view, Bundle savedInstanceState) {
        mDbHelper = SmartGroceryDBHelper.getInstance(getActivity().getApplicationContext());

        fillData();
        registerForContextMenu(getListView());
        Button addnote = (Button)rootView.findViewById(R.id.addnotebutton);
        addnote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                createNote();
            }
        });

    }

    private void createNote() {
        Intent notes_edit = new Intent(getActivity(), NoteEdit.class);
        startActivity(notes_edit);
    }

    @Override
    public void onListItemClick(ListView l, View v, int position, long id) {
        super.onListItemClick(l, v, position, id);
        Intent i = new Intent(getActivity(), NoteEdit.class);
        i.putExtra(mDbHelper.KEY_ROWID, id);
        startActivityForResult(i, ACTIVITY_EDIT);
    }

    private void fillData() {
        // Get all of the notes from the database and create the item list
        Cursor notesCursor = mDbHelper.fetchAllNotes();
        getActivity().startManagingCursor(notesCursor);


        String[] from = new String[] { mDbHelper.KEY_TITLE ,mDbHelper.KEY_DATE};
        int[] to = new int[] { R.id.text1 , R.id.date_row};

        // Now create an array adapter and set it to display using our row
        SimpleCursorAdapter notes =
                new SimpleCursorAdapter(getActivity(), R.layout.notes_row, notesCursor, from, to);
        setListAdapter(notes);
    }

    @Override
    public void onCreateContextMenu(ContextMenu menu, View v,
                                    ContextMenu.ContextMenuInfo menuInfo) {
        super.onCreateContextMenu(menu, v, menuInfo);
        menu.add(0, DELETE_ID, 0, R.string.menu_delete);
    }

    @Override
    public boolean onContextItemSelected(MenuItem item) {
        switch(item.getItemId()) {
            case DELETE_ID:
                AdapterView.AdapterContextMenuInfo info = (AdapterView.AdapterContextMenuInfo) item.getMenuInfo();
                mDbHelper.deleteNote(info.id);
                fillData();
                return true;
        }
        return super.onContextItemSelected(item);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        fillData();
    }
}
