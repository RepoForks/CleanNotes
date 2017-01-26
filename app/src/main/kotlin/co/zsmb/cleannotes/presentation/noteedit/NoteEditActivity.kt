package co.zsmb.cleannotes.presentation.noteedit

import android.os.Bundle
import android.view.MenuItem
import co.zsmb.cleannotes.R
import co.zsmb.cleannotes.di.application.NotesApplication
import co.zsmb.cleannotes.di.noteedit.DaggerNoteEditActivityComponent
import co.zsmb.cleannotes.di.noteedit.NoteEditActivityComponent
import co.zsmb.cleannotes.presentation.base.BaseView
import kotlinx.android.synthetic.main.activity_note_edit.*

class NoteEditActivity : BaseView<NoteEditPresenter, NoteEditActivityComponent>(), NoteEditView {

    private var noteId = 0

    override fun createComponent(): NoteEditActivityComponent
            = DaggerNoteEditActivityComponent.builder()
            .applicationComponent(NotesApplication.applicationComponent)
            .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_note_edit)
        supportActionBar?.setDisplayHomeAsUpEnabled(true)
        presenter.bind(this)

        if (!intent.hasExtra("id")) {
            presenter.createNote()
        }
        else {
            val id = intent.getIntExtra("id", 0)
            presenter.loadNote(id)
        }
    }

    override fun onOptionsItemSelected(item: MenuItem) =
            when (item.itemId) {
                android.R.id.home -> {
                    finish()
                    true
                }
                else -> super.onOptionsItemSelected(item)
            }

    override fun onPause() {
        super.onPause()
        saveNote()
    }

    override fun displayNote(note: EditableNote) {
        noteId = note.id

        etTitle.setText(note.title)
        etContent.setText(note.content)
    }

    private fun saveNote() {
        val title = etTitle.text.toString().trim()
        val content = etContent.text.toString().trim()

        val note = EditableNote(noteId, title, content)

        presenter.saveNote(note)
    }

}
