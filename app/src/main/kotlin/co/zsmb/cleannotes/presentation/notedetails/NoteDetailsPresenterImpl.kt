package co.zsmb.cleannotes.presentation.notedetails

import android.util.Log
import co.zsmb.cleannotes.di.notedetails.NoteDetailsUseCases
import co.zsmb.cleannotes.presentation.base.BasePresenter
import co.zsmb.cleannotes.presentation.base.Navigator
import co.zsmb.cleannotes.presentation.noteedit.NoteEditActivity
import io.reactivex.Scheduler
import java.util.*
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
                    val calendar = Calendar.getInstance()
                    calendar.timeInMillis = it.timestamp

                    val year = calendar[Calendar.YEAR]
                    val month = calendar[Calendar.MONTH] + 1
                    val day = calendar[Calendar.DAY_OF_MONTH]
                    val hour = calendar[Calendar.HOUR_OF_DAY]
                    val minute = calendar[Calendar.MINUTE]

                    val timestamp = String.format("%d-%02d-%02d %02d:%02d", year, month, day, hour, minute)

                    DetailedNote(it.id, it.title, it.content, timestamp)
                }
                .observeOn(mainScheduler)
                .subscribe(
                        { note ->
                            showNote(note)
                        },
                        { error ->
                            Log.e(TAG, "Could not load note")
                            view?.close()
                        }
                )
    }

    private fun DetailedNote.isBlank() = title.isBlank() && content.isBlank()

    private fun showNote(note: DetailedNote) {
        if (note.isBlank()) {
            view?.close()
        }
        else {
            view?.displayNote(note)
        }
    }

}
