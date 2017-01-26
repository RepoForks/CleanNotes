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
import io.reactivex.disposables.Disposable
import kotlinx.android.synthetic.main.activity_notes.*
import org.jetbrains.anko.onClick

class NotesActivity : BaseView<NotesPresenter, NotesActivityComponent>(), NotesView {

    private val adapter = NotesAdapter()

    private var restorePosition: Int = -1

    lateinit var itemClickSubscription: Disposable

    override fun createComponent(): NotesActivityComponent
            = DaggerNotesActivityComponent.builder()
            .applicationComponent(NotesApplication.applicationComponent)
            .build()

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_notes)
        presenter.bind(this)

        setupRecyclerView(savedInstanceState)

        itemClickSubscription = adapter.itemSelections.subscribe { presenter.openNote(it) }

        fab.onClick { presenter.createNote() }
    }

    override fun onDestroy() {
        itemClickSubscription.dispose()
        super.onDestroy()
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

    override fun onSaveInstanceState(outState: Bundle) {
        if (!adapter.isEmpty) {
            outState.putInt("position", recyclerView.scrollPosition)
        }

        super.onSaveInstanceState(outState)
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

}
