package izzatismail.com.persistence;

import android.arch.lifecycle.LiveData;
import android.content.Context;
import android.util.Log;

import izzatismail.com.async.DeleteAsyncTask;
import izzatismail.com.async.UpdateAsyncTask;
import izzatismail.com.async.insertAsyncTask;

import java.util.List;

import izzatismail.com.models.Note;

public class NoteRepository {
    //Methods to call the Dao
    //Best used for multiple data source

    private NoteDatabase mNoteDatabase;

    public NoteRepository(Context context) {
        mNoteDatabase = NoteDatabase.getInstance(context);
    }
    public void insertNoteTask(Note note){
        new insertAsyncTask(mNoteDatabase.getNoteDao()).execute(note);
    }

    public void updateNote(Note note){
        new UpdateAsyncTask(mNoteDatabase.getNoteDao()).execute(note);
    }

    public LiveData<List<Note>> retrieveNoteTask(){
        return mNoteDatabase.getNoteDao().getNotes(); //Get a reference to the Dao, & then use the getNotes() method
    }

    public void deleteNote(Note note){
        new DeleteAsyncTask(mNoteDatabase.getNoteDao()).execute(note);
    }
}
