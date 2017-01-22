package co.zsmb.cleannotes.presentation.noteedit

import co.zsmb.cleannotes.di.noteedit.NoteEditUseCases
import co.zsmb.cleannotes.domain.DomainNote
import co.zsmb.cleannotes.presentation.base.BasePresenter
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

class NoteEditPresenterImpl @Inject constructor(
        private val useCases: NoteEditUseCases,
        private val mainScheduler: Scheduler)
    : BasePresenter<NoteEditView>(), NoteEditPresenter {

    private fun showNote(note: EditableNote) {
        view?.displayNote(note)
    }

    override fun createNote() {
        subscriptions += useCases.createNoteUseCase().execute(Unit).subscribeToShow()
    }

    override fun loadNote(id: Int) {
        subscriptions += useCases.getNoteUseCase().execute(id).subscribeToShow()
    }

    private fun Observable<DomainNote>.subscribeToShow() = this
            .map { EditableNote(it.id, it.title, it.content) }
            .observeOn(mainScheduler)
            .subscribe {
                showNote(it)
            }

    override fun saveNote(note: EditableNote) {
        val domainNote = DomainNote(note.id, note.title, note.content)

        subscriptions += useCases.updateNoteUseCase().execute(domainNote)
                .observeOn(mainScheduler)
                .subscribe() // throw away the result
    }

}
