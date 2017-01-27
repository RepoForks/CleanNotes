package co.zsmb.cleannotes.presentation.notedetails

import android.graphics.Color
import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import android.widget.TextView
import co.zsmb.cleannotes.R
import co.zsmb.cleannotes.di.application.NotesApplication
import co.zsmb.cleannotes.di.notedetails.DaggerNoteDetailsActivityComponent
import co.zsmb.cleannotes.di.notedetails.NoteDetailsActivityComponent
import co.zsmb.cleannotes.presentation.base.BaseView
import kotlinx.android.synthetic.main.activity_note_details.*
import org.jetbrains.anko.textColor

class NoteDetailsActivity : BaseView<NoteDetailsPresenter, NoteDetailsActivityComponent>(), NoteDetailsView {

    companion object {
        val COLOR_DEFAULT = Color.GRAY
        val COLOR_FADED = Color.LTGRAY
    }

    private var noteId: Int = 0

    override fun createComponent(): NoteDetailsActivityComponent
            = DaggerNoteDetailsActivityComponent.builder()
            .applicationComponent(NotesApplication.applicationComponent)
            .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_details)
        presenter.bind(this)

        if (!intent.hasExtra("id")) {
            throw RuntimeException("NoteDetailsActivity started without noteId parameter")
        }

        noteId = intent.getIntExtra("id", 0)
    }

    override fun onResume() {
        super.onResume()

        presenter.loadNote(noteId)
    }

    override fun onPrepareOptionsMenu(menu: Menu?): Boolean {
        menuInflater.inflate(R.menu.menu_notedetails, menu)
        return super.onPrepareOptionsMenu(menu)
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                R.id.action_edit_note -> {
                    presenter.editNote(noteId)
                    true
                }
                R.id.action_delete_note -> {
                    presenter.deleteNote(noteId)
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    override fun displayNote(note: DetailedNote) {
        noteId = note.id
        tvTimestamp.text = note.timestamp

        setTextOrPlaceholder(tvTitle, note.title, "Untitled")
        setTextOrPlaceholder(tvContent, note.content, "No content")
    }

    private fun setTextOrPlaceholder(tv: TextView, text: String, placeholder: String) {
        if (text.isEmpty()) {
            tv.text = placeholder
            tv.textColor = COLOR_FADED
        }
        else {
            tv.text = text
            tv.textColor = COLOR_DEFAULT
        }
    }

}
