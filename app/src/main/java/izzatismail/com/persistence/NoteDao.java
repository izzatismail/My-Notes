package izzatismail.com.persistence;

import android.arch.lifecycle.LiveData;
import android.arch.persistence.room.Dao;
import android.arch.persistence.room.Delete;
import android.arch.persistence.room.Insert;
import android.arch.persistence.room.Query;
import android.arch.persistence.room.Update;

import java.util.List;

import izzatismail.com.models.Note;

@Dao
public interface NoteDao {
    @Insert
    long[] insertNotes(Note... notes); //By default, no need to specify return type (void) ... is the same as [] for array

    @Query("SELECT * FROM notes")
    LiveData<List<Note>> getNotes();

    /* If you want to use custom query
    @Query("SELECT * FROM notes where id = :id")
    List<Note> getNotesWithCustomQuery(int id);
    */

    @Delete
    int delete(Note... notes); //int to show how many rows are deleted

    @Update
    int update(Note... notes);

}
