package co.zsmb.cleannotes.presentation.notedetails

import android.util.Log
import co.zsmb.cleannotes.domain.usecase.GetNoteUseCase
import co.zsmb.cleannotes.presentation.base.BasePresenter
import co.zsmb.cleannotes.presentation.base.Navigator
import co.zsmb.cleannotes.presentation.noteedit.NoteEditActivity
import io.reactivex.Scheduler
import javax.inject.Inject

class NoteDetailsPresenterImpl @Inject constructor(
        val navigator: Navigator,
        val getNoteUseCase: GetNoteUseCase,
        val mainScheduler: Scheduler)
    : BasePresenter<NoteDetailsView>(), NoteDetailsPresenter {

    override fun editNote(noteId: Int) {
        navigator.goto(NoteEditActivity::class, "id" to noteId)
    }

    companion object {
        val TAG = "NDPresenterImpl"
    }

    private fun showNote(note: DetailedNote) {
        view?.displayNote(note)
    }

    override fun loadNote(id: Int) {
        subscriptions += getNoteUseCase.execute(id)
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

}
