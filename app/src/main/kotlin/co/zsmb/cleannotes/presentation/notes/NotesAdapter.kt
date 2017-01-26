package co.zsmb.cleannotes.presentation.notes

import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import co.zsmb.cleannotes.R
import co.zsmb.cleannotes.presentation.util.inflate
import org.jetbrains.anko.onClick

class NotesAdapter(private val listener: INotesAdapter) : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    private var notes: List<PresentableNote> = listOf()

    val isEmpty: Boolean
        get() = notes.isEmpty()

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]

        holder.title.text = note.title
        holder.content.text = note.content

        holder.itemView.onClick {
            listener.onNoteChosen(note)
        }
    }

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ViewHolder {
        val layout = parent.inflate(R.layout.item_note)
        return ViewHolder(layout)
    }

    fun setNotes(notes: List<PresentableNote>) {
        this.notes = notes
        notifyDataSetChanged()
    }

    class ViewHolder(view: View) : RecyclerView.ViewHolder(view) {

        val title: TextView = view.findViewById(R.id.tvTitle) as TextView
        val content: TextView = view.findViewById(R.id.tvContent) as TextView

    }

    interface INotesAdapter {

        fun onNoteChosen(note: PresentableNote)

    }

}
