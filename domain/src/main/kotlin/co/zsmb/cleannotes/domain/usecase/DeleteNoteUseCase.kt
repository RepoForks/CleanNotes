package co.zsmb.cleannotes.domain.usecase

import co.zsmb.cleannotes.domain.NoteRepository
import co.zsmb.cleannotes.domain.base.UseCase
import io.reactivex.Scheduler

import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
        private val noteRepository: NoteRepository,
        scheduler: Scheduler)
    : UseCase<Boolean, Int>(scheduler) {

    override fun createObservable(params: Int) = noteRepository.delete(params)

}
