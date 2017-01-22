package co.zsmb.cleannotes.domain.usecase

import co.zsmb.cleannotes.domain.DomainNote
import co.zsmb.cleannotes.domain.NotesRepository
import co.zsmb.cleannotes.domain.base.UseCase
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

class GetNoteUseCase @Inject constructor(
        val notesRepository: NotesRepository,
        scheduler: Scheduler)
    : UseCase<DomainNote, Int>(scheduler) {

    override fun createObservable(params: Int): Observable<DomainNote>
            = notesRepository.get(params)

}