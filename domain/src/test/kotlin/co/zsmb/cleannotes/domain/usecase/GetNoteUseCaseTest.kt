package co.zsmb.cleannotes.domain.usecase

import co.zsmb.cleannotes.domain.DomainNote
import co.zsmb.cleannotes.domain.NoteRepository
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.never
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetNoteUseCaseTest {

    @Mock lateinit var noteRepository: NoteRepository

    lateinit var getNoteUseCase: GetNoteUseCase

    private val ID = 123
    private val NOTE = DomainNote(ID, "title", "content", ID.toLong())

    @Before
    fun setUp() {
        whenever(noteRepository.get(ID)) doReturn Single.just(NOTE)

        getNoteUseCase = GetNoteUseCase(noteRepository, Schedulers.trampoline())
    }

    @Test
    fun execute_getsNoteFromRepository() {
        getNoteUseCase.testExecute()

        verify(noteRepository).get(ID)
    }

    @Test
    fun execute_doesNotUseMassQuery() {
        getNoteUseCase.testExecute()

        verify(noteRepository, never()).getAll()
    }

    @Test
    fun execute_returnsRepositoryResultUntouched() {
        val result = getNoteUseCase.testExecute()

        assertEquals(NOTE, result)
    }

    private fun GetNoteUseCase.testExecute() = execute(ID).blockingGet()

}
