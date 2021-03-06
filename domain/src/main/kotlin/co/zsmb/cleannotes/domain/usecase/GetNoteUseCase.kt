package co.zsmb.cleannotes.domain.usecase

import co.zsmb.cleannotes.domain.DomainNote
import co.zsmb.cleannotes.domain.NoteRepository
import co.zsmb.cleannotes.domain.base.UseCase
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject

class GetNoteUseCase @Inject constructor(
        private val noteRepository: NoteRepository,
        scheduler: Scheduler)
    : UseCase<DomainNote, Int>(scheduler) {

    override fun createObservable(params: Int): Single<DomainNote>
            = noteRepository.get(params)

}