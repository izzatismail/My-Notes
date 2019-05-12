package izzatismail.com.adapters;

import android.support.annotation.NonNull;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.ArrayList;

import izzatismail.com.R;
import izzatismail.com.models.Note;

public class NotesRecyclerAdapter extends RecyclerView.Adapter<NotesRecyclerAdapter.ViewHolder>{

    private ArrayList<Note> mNotes = new ArrayList<>(); //Data Structure
    private OnNoteListener mOnNoteListener;

    public NotesRecyclerAdapter(ArrayList<Note> Notes, OnNoteListener onNoteListner) {
        this.mNotes = Notes;
        this.mOnNoteListener = onNoteListner;
    }

    /* Required methods for RecyclerView*/
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup viewGroup, int i) {
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.layout_note_list_item, viewGroup, false);
        return new ViewHolder(view, mOnNoteListener);
    }//For creating ViewHolder object

    @Override
    public void onBindViewHolder(@NonNull ViewHolder viewHolder, int i) {
     viewHolder.timestamp.setText(mNotes.get(i).getTimestamp());
     viewHolder.title.setText(mNotes.get(i).getTitle());
    }//Bind every entry in the ArrayList

    @Override
    public int getItemCount() {
        return mNotes.size();
    }//Return the ArrayList size

    //For best practice in OnClickListener, use implements interface
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {

        TextView title, timestamp;
        OnNoteListener onNoteListener;

        public ViewHolder(@NonNull View itemView, OnNoteListener onNoteListner) {
            super(itemView);
            title=itemView.findViewById(R.id.note_title);
            timestamp=itemView.findViewById(R.id.note_timestamp);
            this.onNoteListener = onNoteListner;

            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onNoteListener.onNoteClick(getAdapterPosition());
        }
    }

    /* Best practice for onClickListener for best performance */
    public interface OnNoteListener{
        void onNoteClick(int position);
    }
}
