package co.zsmb.cleannotes.presentation.notedetails

import android.util.Log
import co.zsmb.cleannotes.di.notedetails.NoteDetailsUseCases
import co.zsmb.cleannotes.presentation.base.BasePresenter
import co.zsmb.cleannotes.presentation.base.Navigator
import co.zsmb.cleannotes.presentation.noteedit.NoteEditActivity
import io.reactivex.Scheduler
import javax.inject.Inject

class NoteDetailsPresenterImpl @Inject constructor(
        private val navigator: Navigator,
        private val useCases: NoteDetailsUseCases,
        private val mainScheduler: Scheduler)
    : BasePresenter<NoteDetailsView>(), NoteDetailsPresenter {

    companion object {
        private val TAG = "NDPresenterImpl"
    }

    override fun deleteNote(noteId: Int) {
        subscriptions += useCases.deleteNoteUseCase().execute(noteId)
                .observeOn(mainScheduler)
                .subscribe { success ->
                    view?.close()
                }
    }

    override fun editNote(noteId: Int) {
        navigator.goto(NoteEditActivity::class, "id" to noteId)
    }

    override fun loadNote(id: Int) {
        subscriptions += useCases.getNoteUseCase().execute(id)
                .map {
                    DetailedNote(it.id, it.title, it.content)
                }
                .observeOn(mainScheduler)
                .subscribe(
                        { note ->
                            showNote(note)
                        },
                        { error ->
                            Log.e(TAG, "Could not load note")
                        }
                )
    }

    private fun showNote(note: DetailedNote) {
        view?.displayNote(note)
    }

}
