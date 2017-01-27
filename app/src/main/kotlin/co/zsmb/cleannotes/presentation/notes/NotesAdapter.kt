package co.zsmb.cleannotes.presentation.notes

import android.graphics.Color
import android.support.v7.widget.RecyclerView
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import co.zsmb.cleannotes.R
import co.zsmb.cleannotes.presentation.util.inflate
import io.reactivex.subjects.PublishSubject
import org.jetbrains.anko.onClick
import org.jetbrains.anko.textColor

class NotesAdapter : RecyclerView.Adapter<NotesAdapter.ViewHolder>() {

    companion object {
        val COLOR_DEFAULT = Color.GRAY
        val COLOR_FADED = Color.LTGRAY
    }

    private var notes: List<PresentableNote> = listOf()

    val isEmpty: Boolean
        get() = notes.isEmpty()

    val itemSelections: PublishSubject<PresentableNote> = PublishSubject.create()

    override fun getItemCount() = notes.size

    override fun onBindViewHolder(holder: ViewHolder, position: Int) {
        val note = notes[position]

        var title = note.title
        var color = COLOR_DEFAULT

        if (title.isBlank()) {
            title = "Untitled"
            color = COLOR_FADED
        }

        holder.title.text = title
        holder.title.textColor = color

        holder.content.text = note.content

        holder.itemView.onClick {
            itemSelections.onNext(note)
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

}
