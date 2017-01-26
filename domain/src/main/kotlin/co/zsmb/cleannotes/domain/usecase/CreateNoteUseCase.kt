package co.zsmb.cleannotes.domain.usecase

import co.zsmb.cleannotes.domain.DomainNote
import co.zsmb.cleannotes.domain.NotesRepository
import co.zsmb.cleannotes.domain.base.UseCase
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject

class CreateNoteUseCase @Inject constructor(
        private val notesRepository: NotesRepository,
        scheduler: Scheduler)
    : UseCase<DomainNote, Unit>(scheduler) {

    override fun createObservable(params: Unit): Single<DomainNote> {
        return notesRepository.add(DomainNote(0, "", ""))
                .map { notesRepository.get(it).blockingGet() }
    }

}
