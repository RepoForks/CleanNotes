package co.zsmb.cleannotes.domain.usecase

import co.zsmb.cleannotes.domain.DomainNote
import co.zsmb.cleannotes.domain.NotesRepository
import co.zsmb.cleannotes.domain.base.UseCase
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

class CreateNoteUseCase @Inject constructor(
        val notesRepository: NotesRepository,
        scheduler: Scheduler)
    : UseCase<DomainNote, Unit>(scheduler) {

    override fun createObservable(params: Unit): Observable<DomainNote> {
        return notesRepository.add(DomainNote(0, "", ""))
                .flatMap { notesRepository.get(it) }
    }

}
