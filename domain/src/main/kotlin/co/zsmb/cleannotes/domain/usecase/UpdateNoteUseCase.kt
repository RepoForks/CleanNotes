package co.zsmb.cleannotes.domain.usecase

import co.zsmb.cleannotes.domain.DomainNote
import co.zsmb.cleannotes.domain.NotesRepository
import co.zsmb.cleannotes.domain.base.UseCase
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

class UpdateNoteUseCase @Inject constructor(
        val notesRepository: NotesRepository,
        scheduler: Scheduler)
    : UseCase<Int, DomainNote>(scheduler) {

    override fun createObservable(params: DomainNote): Observable<Int> {
        return notesRepository.update(params)
    }

}
