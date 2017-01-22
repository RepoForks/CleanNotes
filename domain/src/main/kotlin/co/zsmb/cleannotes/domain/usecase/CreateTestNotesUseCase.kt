package co.zsmb.cleannotes.domain.usecase

import co.zsmb.cleannotes.domain.DomainNote
import co.zsmb.cleannotes.domain.NotesRepository
import co.zsmb.cleannotes.domain.base.UseCase
import io.reactivex.Observable
import io.reactivex.Scheduler
import javax.inject.Inject

class CreateTestNotesUseCase @Inject constructor(
        val notesRepository: NotesRepository,
        scheduler: Scheduler)
    : UseCase<Int, List<DomainNote>>(scheduler) {

    override fun createObservable(params: List<DomainNote>): Observable<Int> {
        notesRepository.addAll(params)
        return Observable.just(params.size)
    }

}
