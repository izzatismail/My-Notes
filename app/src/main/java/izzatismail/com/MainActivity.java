package izzatismail.com;

import android.arch.lifecycle.Observer;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.support.v7.widget.helper.ItemTouchHelper;
import android.util.Log;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import izzatismail.com.adapters.NotesRecyclerAdapter;
import izzatismail.com.models.Note;
import izzatismail.com.persistence.NoteRepository;
import izzatismail.com.util.VerticalSpacingItemDecorator;

public class MainActivity extends AppCompatActivity implements
        NotesRecyclerAdapter.OnNoteListener,
        FloatingActionButton.OnClickListener {

    private static final String TAG = "MainActivity";

    /* UI Components */
    private RecyclerView mRecyclerView;

    /* Variables */
    private ArrayList<Note> mNotes = new ArrayList<>();
    private NotesRecyclerAdapter mNoteRecyclerAdapter;
    private NoteRepository mNoteRepository;
    
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        mRecyclerView = findViewById(R.id.recyclerView);

        findViewById(R.id.fabAddNote).setOnClickListener(this);

        initRecyclerView();
        mNoteRepository = new NoteRepository(this);
        retrieveNotes();

        setSupportActionBar((Toolbar)findViewById(R.id.note_toolbar));
        setTitle("Notes");
    }

    private void retrieveNotes(){
        mNoteRepository.retrieveNoteTask().observe(this, new Observer<List<Note>>() {
            @Override
            public void onChanged(@Nullable List<Note> notes) {
                if(mNotes.size() > 0){
                    mNotes.clear();
                }
                if(mNotes != null){
                    mNotes.addAll(notes);
                }
                mNoteRecyclerAdapter.notifyDataSetChanged();
            }
        });//Any change made to the LiveData object, this onChanged will trigger to retrieve LiveData object right away
    }

    private void initRecyclerView(){
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        mRecyclerView.setLayoutManager(linearLayoutManager);
        VerticalSpacingItemDecorator itemDecorator = new VerticalSpacingItemDecorator(10);
        mRecyclerView.addItemDecoration(itemDecorator);
        new ItemTouchHelper(itemTouchHelperCallBack).attachToRecyclerView(mRecyclerView);
        mNoteRecyclerAdapter = new NotesRecyclerAdapter(mNotes, this);
        mRecyclerView.setAdapter(mNoteRecyclerAdapter);
    }

    @Override
    public void onNoteClick(int position) {
        Log.d(TAG, "onNoteClick: clicked " + position);
        mNotes.get(position);
        Intent intent = new Intent(this, NoteActivity.class);
        intent.putExtra("selected_note", mNotes.get(position));
        startActivity(intent);
    }

    /* Lifecycle */
    @Override
    protected void onResume() {
        super.onResume();
        Log.d(TAG, "onResume: called");
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        Log.d(TAG, "onDestroy: called");
    }

    @Override
    protected void onPause() {
        super.onPause();
        Log.d(TAG, "onPause: called");
    }

    @Override
    protected void onStart() {
        super.onStart();
        Log.d(TAG, "onStart: called");
    }

    @Override
    protected void onStop() {
        super.onStop();
        Log.d(TAG, "onStop: called");
    }


    @Override
    public void onClick(View v) {
        startActivity(new Intent(this, NoteActivity.class));
    }

    private void deleteNote(Note note){
        mNotes.remove(note);
        mNoteRecyclerAdapter.notifyDataSetChanged();

        mNoteRepository.deleteNote(note);
    }

    private ItemTouchHelper.SimpleCallback  itemTouchHelperCallBack = new ItemTouchHelper.SimpleCallback(0, ItemTouchHelper.RIGHT) {
        @Override
        public boolean onMove(@NonNull RecyclerView recyclerView, @NonNull RecyclerView.ViewHolder viewHolder, @NonNull RecyclerView.ViewHolder viewHolder1) {
            return false;
        }

        @Override
        public void onSwiped(@NonNull RecyclerView.ViewHolder viewHolder, int i) {
            deleteNote(mNotes.get(viewHolder.getAdapterPosition()));
        }
    };
}
