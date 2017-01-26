package co.zsmb.cleannotes.domain.usecase

import co.zsmb.cleannotes.domain.DomainNote
import co.zsmb.cleannotes.domain.NoteRepository
import com.nhaarman.mockito_kotlin.*
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetAllNotesUseCaseTest {

    @Mock lateinit var noteRepository: NoteRepository

    lateinit var getAllNotesUseCase: GetAllNotesUseCase

    private val notes = listOf(
            DomainNote(1, "title1", "content1", 1),
            DomainNote(2, "title2", "content2", 2),
            DomainNote(3, "title3", "content3", 3)
    )

    @Before
    fun setUp() {
        whenever(noteRepository.getAll()) doReturn Single.just(notes)

        getAllNotesUseCase = GetAllNotesUseCase(noteRepository, Schedulers.trampoline())
    }

    @Test
    fun execute_getsNotesFromRepository() {
        getAllNotesUseCase.testExecute()

        verify(noteRepository).getAll()
    }

    @Test
    fun execute_doesNotUseIndividualQueries() {
        getAllNotesUseCase.testExecute()

        verify(noteRepository, never()).get(any())
    }

    @Test
    fun execute_returnsRepositoryResultUntouched() {
        val result = getAllNotesUseCase.testExecute()

        assertEquals(notes, result)
    }

    private fun GetAllNotesUseCase.testExecute() = execute(Unit).blockingGet()

}
