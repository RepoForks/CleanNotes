package co.zsmb.cleannotes.presentation.notes

import co.zsmb.cleannotes.di.notes.NotesUseCases
import co.zsmb.cleannotes.presentation.base.BasePresenter
import co.zsmb.cleannotes.presentation.base.Navigator
import co.zsmb.cleannotes.presentation.notedetails.NoteDetailsActivity
import co.zsmb.cleannotes.presentation.noteedit.NoteEditActivity
import io.reactivex.Scheduler
import javax.inject.Inject

class NotesPresenterImpl @Inject constructor(
        private val navigator: Navigator,
        private val useCases: NotesUseCases,
        private val mainScheduler: Scheduler)
    : BasePresenter<NotesView>(), NotesPresenter {

    override fun openNote(note: PresentableNote) {
        navigator.goto(NoteDetailsActivity::class, "id" to note.id)
    }

    override fun loadNotes() {
        subscriptions += useCases.getAllNotesUseCase()
                .execute(Unit)
                .map {
                    it.map { PresentableNote(it.id, it.title, it.content) }.reversed()
                }
                .observeOn(mainScheduler)
                .subscribe { notes -> showNotes(notes) }
    }

    private fun showNotes(notes: List<PresentableNote>) {
        view?.displayNotes(notes)
    }

    override fun createNote() {
        navigator.goto(NoteEditActivity::class)
    }

}
