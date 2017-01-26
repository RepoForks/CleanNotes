package co.zsmb.cleannotes.domain.usecase

import co.zsmb.cleannotes.domain.NotesRepository
import co.zsmb.cleannotes.domain.base.UseCase
import io.reactivex.Scheduler

import javax.inject.Inject

class DeleteNoteUseCase @Inject constructor(
        private val notesRepository: NotesRepository,
        scheduler: Scheduler)
    : UseCase<Boolean, Int>(scheduler) {

    override fun createObservable(params: Int) = notesRepository.delete(params)

}
