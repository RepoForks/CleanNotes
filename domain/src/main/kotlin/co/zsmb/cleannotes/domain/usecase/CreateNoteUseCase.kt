package co.zsmb.cleannotes.domain.usecase

import co.zsmb.cleannotes.domain.DomainNote
import co.zsmb.cleannotes.domain.NoteRepository
import co.zsmb.cleannotes.domain.base.UseCase
import io.reactivex.Scheduler
import io.reactivex.Single
import javax.inject.Inject

class CreateNoteUseCase @Inject constructor(
        private val noteRepository: NoteRepository,
        scheduler: Scheduler)
    : UseCase<DomainNote, Unit>(scheduler) {

    override fun createObservable(params: Unit): Single<DomainNote> {
        val timestamp = System.currentTimeMillis()
        val note = DomainNote(0, "", "", timestamp)

        println("gonna return")
        return noteRepository.add(note)
                .map { println("mapping");noteRepository.get(it).blockingGet() }
    }

}
