package co.zsmb.cleannotes.domain.usecase

import co.zsmb.cleannotes.domain.NoteRepository
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

@RunWith(Parameterized::class)
class DeleteNoteUseCaseParameterizedTest(val id: Int) {

    @get: Rule val rule: MockitoRule = MockitoJUnit.rule()

    @Mock lateinit var noteRepository: NoteRepository

    lateinit var deleteNoteUseCase: DeleteNoteUseCase

    @Before
    fun setUp() {
        deleteNoteUseCase = DeleteNoteUseCase(noteRepository, Schedulers.trampoline())
    }

    @Test
    fun execute_deletesNoteFromRepository() {
        whenever(noteRepository.delete(any())) doReturn Single.just(true)

        deleteNoteUseCase.execute(id).blockingGet()

        verify(noteRepository).delete(id)
    }

    @Test
    fun execute_returnsTrueRepositoryResult() {
        whenever(noteRepository.delete(any())) doReturn Single.just(true)

        val result = deleteNoteUseCase.execute(id).blockingGet()

        assertEquals(true, result)
    }

    @Test
    fun execute_returnsFalseRepositoryResult() {
        whenever(noteRepository.delete(any())) doReturn Single.just(false)

        val result = deleteNoteUseCase.execute(id).blockingGet()

        assertEquals(false, result)
    }

    companion object {

        @JvmStatic @Parameterized.Parameters
        fun data() = listOf(-100, -50, -2, -1, 0, 1, 2, 50, 100)

    }

}
