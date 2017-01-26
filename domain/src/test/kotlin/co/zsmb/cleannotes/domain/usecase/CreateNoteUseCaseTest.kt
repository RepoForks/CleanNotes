package co.zsmb.cleannotes.domain.usecase

import co.zsmb.cleannotes.domain.DomainNote
import co.zsmb.cleannotes.domain.NoteRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
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
class CreateNoteUseCaseTest {

    @Mock lateinit var noteRepository: NoteRepository

    lateinit var createNoteUseCase: CreateNoteUseCase

    private val ID = 123
    private val NOTE = DomainNote(ID, "title", "content", ID.toLong())

    @Before
    fun setUp() {
        whenever(noteRepository.add(any())) doReturn Single.just(ID)
        whenever(noteRepository.get(ID)) doReturn Single.just(NOTE)

        createNoteUseCase = CreateNoteUseCase(noteRepository, Schedulers.trampoline())
    }

    @Test
    fun execute_createsNoteInRepository() {
        createNoteUseCase.testExecute()

        verify(noteRepository).add(any())
    }

    @Test
    fun execute_fetchesNoteByIdItReceivedWhenAdding() {
        createNoteUseCase.testExecute()

        verify(noteRepository).get(ID)
    }

    @Test
    fun execute_returnsNoteReceiverFromRepository() {
        val result = createNoteUseCase.testExecute()

        assertEquals(NOTE, result)
    }

    private fun CreateNoteUseCase.testExecute() = execute(Unit).blockingGet()

}
