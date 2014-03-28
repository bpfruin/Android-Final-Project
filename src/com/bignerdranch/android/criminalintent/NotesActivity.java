package com.bignerdranch.android.criminalintent;

import android.app.Activity;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.NavUtils;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.MenuItem;
import android.widget.EditText;
import android.widget.TextView;

/**
 * Created by bpfruin on 8/26/13.
 */
public class NotesActivity extends Activity {


    private long mUserId;
    private EditText mNotesField;
    private TextView mNoteTitle;
    private Resto mResto;
    private Callbacks mCallbacks;

    public static final String NOTES_FOR_RESTO = "fruin.brian.notes_for_resto";
    public static final String TAG = "NotesActivity";
    public static final String RESTO_NOTE = "note";

    public interface Callbacks {
        void onRestoUpdated(Resto resto);
    }

    public void setNoteForIntent(String note){
        Intent data = new Intent();
        data.putExtra(NOTES_FOR_RESTO, note);
        setResult(RESULT_OK,data);
    }


    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_notes);

        //mCallbacks = (Callbacks)this;



        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.HONEYCOMB) {
            this.getActionBar().setDisplayHomeAsUpEnabled(true);
        }



        mUserId = getIntent().getLongExtra(RestoFragment.EXTRA_USER_ID, -1);

        mResto = RestoDatabase.get(this).getCrime(mUserId);

        mNoteTitle = (TextView) findViewById(R.id.note_title);
        mNoteTitle.setText("Notes for: "+mResto.getName());


        mNotesField = (EditText)findViewById(R.id.note_edit);
        mNotesField.setText(mResto.getNote());
        mNotesField.addTextChangedListener(new TextWatcher() {
            public void onTextChanged(CharSequence c, int start, int before, int count) {
                mResto.setNote(c.toString());
                setNoteForIntent(c.toString());
                //mCallbacks.onRestoUpdated(mResto);
            }

            public void beforeTextChanged(CharSequence c, int start, int count, int after) {
                // this space intentionally left blank
            }

            public void afterTextChanged(Editable c) {
                // this one too
            }
        });





    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                //NavUtils.navigateUpFromSameTask(this);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle savedInstanceState){
        super.onSaveInstanceState(savedInstanceState);
        Log.i(TAG, "onSaveInstanceState");
        savedInstanceState.putString(RESTO_NOTE, mResto.getNote());
    }

//    public void onDestroy(){
//        super.onDestroy();
//        mCallbacks = null;
//    }
}