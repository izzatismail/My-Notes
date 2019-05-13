package izzatismail.com.async;

import android.os.AsyncTask;
import android.util.Log;

import izzatismail.com.models.Note;
import izzatismail.com.persistence.NoteDao;

public class insertAsyncTask extends AsyncTask<Note, Void, Void> {

    private static final String TAG = "insertAsyncTask";

    private NoteDao mDao;
    public insertAsyncTask(NoteDao dao) {
        mDao = dao;
    }

    @Override
    protected Void doInBackground(Note... notes) {
        Log.d(TAG, "doInBackground: thread : " + Thread.currentThread().getName());
        mDao.insertNotes(notes);
        return null;
    }
}

//Best for executing a single task