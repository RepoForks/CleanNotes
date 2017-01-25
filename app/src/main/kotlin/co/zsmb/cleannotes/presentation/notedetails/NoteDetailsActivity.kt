package co.zsmb.cleannotes.presentation.notedetails

import android.os.Bundle
import android.view.Menu
import android.view.MenuItem
import co.zsmb.cleannotes.R
import co.zsmb.cleannotes.di.application.NotesApplication
import co.zsmb.cleannotes.di.notedetails.DaggerNoteDetailsActivityComponent
import co.zsmb.cleannotes.di.notedetails.NoteDetailsActivityComponent
import co.zsmb.cleannotes.presentation.base.BaseView
import kotlinx.android.synthetic.main.activity_note_details.*

class NoteDetailsActivity : BaseView<NoteDetailsPresenter, NoteDetailsActivityComponent>(), NoteDetailsView {

    private var noteId: Int = 0

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

    override fun createComponent(): NoteDetailsActivityComponent
            = DaggerNoteDetailsActivityComponent.builder()
            .applicationComponent(NotesApplication.applicationComponent)
            .build()

    override fun displayNote(note: DetailedNote) {
        noteId = note.id
        tvTitle.text = note.title
        tvContent.text = note.content
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

}
