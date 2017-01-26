package co.zsmb.cleannotes.domain.usecase

import co.zsmb.cleannotes.domain.DomainNote
import co.zsmb.cleannotes.domain.NoteRepository
import co.zsmb.cleannotes.domain.base.UseCase
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject

class GetAllNotesUseCase @Inject constructor(
        private val noteRepository: NoteRepository,
        scheduler: Scheduler)
    : UseCase<List<DomainNote>, Unit>(scheduler) {

    override fun createObservable(params: Unit): Single<List<DomainNote>>
            = noteRepository.getAll()

}
