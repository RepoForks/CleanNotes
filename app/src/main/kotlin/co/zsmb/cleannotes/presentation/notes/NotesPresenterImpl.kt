package co.zsmb.cleannotes.presentation.notes

import co.zsmb.cleannotes.di.notes.NotesUseCases
import co.zsmb.cleannotes.domain.DomainNote
import co.zsmb.cleannotes.presentation.base.BasePresenter
import co.zsmb.cleannotes.presentation.base.Navigator
import co.zsmb.cleannotes.presentation.notedetails.NoteDetailsActivity
import io.reactivex.Scheduler
import javax.inject.Inject

class NotesPresenterImpl @Inject constructor(
        val navigator: Navigator,
        val useCases: NotesUseCases,
        val mainScheduler: Scheduler)
    : BasePresenter<NotesView>(), NotesPresenter {

    override fun openNote(note: PresentableNote) {
        navigator.goto(NoteDetailsActivity::class, "id" to note.id)
    }

    override fun loadNotes() {
        subscriptions += useCases.getAllNotesUseCase()
                .execute(Unit)
                .map {
                    it.map { PresentableNote(it.id, it.title, it.content) }
                }
                .observeOn(mainScheduler)
                .subscribe {
                    showNotes(it)
                }
    }

    private fun showNotes(notes: List<PresentableNote>) {
        view?.displayNotes(notes)
    }

    override fun addTestNotes() {
        val testNotes = (1..100).map { DomainNote(0, "$it $it $it $it $it", "This is the contents of test note $it") }

        subscriptions += useCases.createTestNotesUseCase()
                .execute(testNotes)
                .map {
                    "$it notes added"
                }
                .observeOn(mainScheduler)
                .subscribe {
                    showMessage(it)
                    loadNotes()
                }
    }

    private fun showMessage(message: String) {
        if (message.isBlank()) return
        view?.showMessage(message)
    }

}
