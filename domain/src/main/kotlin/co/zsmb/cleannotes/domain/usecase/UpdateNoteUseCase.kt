package co.zsmb.cleannotes.domain.usecase

import co.zsmb.cleannotes.domain.DomainNote
import co.zsmb.cleannotes.domain.NoteRepository
import co.zsmb.cleannotes.domain.base.UseCase
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject

class UpdateNoteUseCase @Inject constructor(
        private val noteRepository: NoteRepository,
        scheduler: Scheduler)
    : UseCase<Boolean, DomainNote>(scheduler) {

    private fun DomainNote.isBlank() = title.isBlank() && content.isBlank()

    override fun createObservable(params: DomainNote): Single<Boolean> {
        if (params.isBlank()) {
            return noteRepository.delete(params.id)
        }

        val note = params.copy(timestamp = System.currentTimeMillis())
        return noteRepository.update(note)
    }

}


