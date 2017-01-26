package co.zsmb.cleannotes.presentation.noteedit

import co.zsmb.cleannotes.di.noteedit.NoteEditUseCases
import co.zsmb.cleannotes.domain.DomainNote
import co.zsmb.cleannotes.presentation.base.BasePresenter
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject

class NoteEditPresenterImpl @Inject constructor(
        private val useCases: NoteEditUseCases,
        private val mainScheduler: Scheduler)
    : BasePresenter<NoteEditView>(), NoteEditPresenter {

    override fun createNote() {
        subscriptions += useCases.createNoteUseCase().execute(Unit).subscribeToShow()
    }

    override fun loadNote(id: Int) {
        subscriptions += useCases.getNoteUseCase().execute(id).subscribeToShow()
    }

    private fun Single<DomainNote>.subscribeToShow() = this
            .map { EditableNote(it.id, it.title, it.content) }
            .observeOn(mainScheduler)
            .subscribe { note -> showNote(note) }

    private fun showNote(note: EditableNote) {
        view?.displayNote(note)
    }

    override fun saveNote(note: EditableNote) {
        val domainNote = DomainNote(note.id, note.title, note.content, 0)

        subscriptions += useCases.updateNoteUseCase().execute(domainNote)
                .observeOn(mainScheduler)
                .subscribe() // throw away the result
    }

}
