package co.zsmb.cleannotes.presentation.notes

import android.os.Bundle
import android.support.v7.widget.DividerItemDecoration
import android.support.v7.widget.LinearLayoutManager
import android.view.View
import co.zsmb.cleannotes.R
import co.zsmb.cleannotes.di.application.NotesApplication
import co.zsmb.cleannotes.di.notes.DaggerNotesActivityComponent
import co.zsmb.cleannotes.di.notes.NotesActivityComponent
import co.zsmb.cleannotes.presentation.base.BaseView
import co.zsmb.cleannotes.presentation.util.scrollPosition
import kotlinx.android.synthetic.main.activity_notes.*
import org.jetbrains.anko.onClick
import org.jetbrains.anko.toast

class NotesActivity : BaseView<NotesPresenter, NotesActivityComponent>(), NotesView, INotesAdapter {

    private val adapter = NotesAdapter(this)

    private var restorePosition: Int = -1

    override fun onNoteChosen(note: PresentableNote) {
        presenter.openNote(note)
    }

    override fun createComponent(): NotesActivityComponent
            = DaggerNotesActivityComponent.builder()
            .applicationComponent(NotesApplication.applicationComponent)
            .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        presenter.bind(this)

        setupRecyclerView(savedInstanceState)

        fab.onClick { presenter.createNote() }
    }

    override fun onSaveInstanceState(outState: Bundle) {
        if (!adapter.isEmpty) {
            outState.putInt("position", recyclerView.scrollPosition)
        }

        super.onSaveInstanceState(outState)
    }

    private fun setupRecyclerView(savedInstanceState: Bundle?) {
        recyclerView.addItemDecoration(DividerItemDecoration(this, LinearLayoutManager.VERTICAL))
        recyclerView.adapter = adapter
        recyclerView.isDrawingCacheEnabled = true
        recyclerView.drawingCacheQuality = View.DRAWING_CACHE_QUALITY_HIGH
        recyclerView.setItemViewCacheSize(30)

        savedInstanceState?.run {
            if (containsKey("position")) {
                restorePosition = getInt("position")
            }
        }
    }

    override fun onResume() {
        super.onResume()
        presenter.loadNotes()
    }

    override fun displayNotes(notes: List<PresentableNote>) {
        adapter.setNotes(notes)

        if (restorePosition >= 0) {
            recyclerView.scrollPosition = restorePosition
            restorePosition = -1
        }
    }

    override fun showMessage(message: String) {
        toast(message)
    }

}
