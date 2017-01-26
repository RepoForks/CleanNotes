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
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class UpdateNoteUseCaseTest {

    @Captor lateinit var captor: ArgumentCaptor<DomainNote>

    @Mock lateinit var noteRepository: NoteRepository

    lateinit var updateNoteUseCase: UpdateNoteUseCase

    private val ID = 123
    private val EMPTY_ID = 456
    private val BLANK_ID = 789

    private val NOTE = DomainNote(ID, "title", "content", ID.toLong())
    private val EMPTY_NOTE = DomainNote(EMPTY_ID, "", "", EMPTY_ID.toLong())
    private val BLANK_NOTE = DomainNote(BLANK_ID, "   ", "        ", BLANK_ID.toLong())

    private val TRUE = Single.just(true)
    private val FALSE = Single.just(false)

    @Before
    fun setUp() {
        whenever(noteRepository.delete(any())) doReturn TRUE
        whenever(noteRepository.update(any())) doReturn TRUE

        updateNoteUseCase = UpdateNoteUseCase(noteRepository, Schedulers.io())
    }

    @Test
    fun executeWihEmptyNote_deletesNoteInRepositoryInsteadOfUpdating() {
        updateNoteUseCase.execute(EMPTY_NOTE).blockingGet()

        verify(noteRepository).delete(EMPTY_ID)
        verify(noteRepository, never()).update(any())
    }

    @Test
    fun executeWihBlankNote_deletesNoteInRepositoryInsteadOfUpdating() {
        updateNoteUseCase.execute(BLANK_NOTE).blockingGet()

        verify(noteRepository).delete(BLANK_ID)
        verify(noteRepository, never()).update(any())
    }

    @Test
    fun executeWithValidNote_updatesNoteInRepository() {
        updateNoteUseCase.execute(NOTE).blockingGet()

        verify(noteRepository).update(capture(captor))

        val capturedNote = captor.value

        assertEquals(NOTE.id, capturedNote.id)
        assertEquals(NOTE.title, capturedNote.title)
        assertEquals(NOTE.content, capturedNote.content)
    }

    @Test
    fun executeWithValidNote_doesNotDeleteNoteInRepository() {
        updateNoteUseCase.execute(NOTE).blockingGet()

        verify(noteRepository, never()).delete(any())
    }

    @Test
    fun executeGivesNoteCurrentTimestamp() {
        val time = System.currentTimeMillis()

        updateNoteUseCase.execute(NOTE).blockingGet()

        verify(noteRepository).update(capture(captor))

        val capturedNote = captor.value

        assert(capturedNote.timestamp >= time)
    }

    @Test
    fun executeWithValidNote_returnsTrueResult() {
        reset(noteRepository)
        whenever(noteRepository.update(any())) doReturn TRUE

        val result = updateNoteUseCase.execute(NOTE).blockingGet()

        assertEquals(true, result)
    }

    @Test
    fun executeWithValidNote_returnsFalseResult() {
        reset(noteRepository)
        whenever(noteRepository.update(any())) doReturn FALSE

        val result = updateNoteUseCase.execute(NOTE).blockingGet()

        assertEquals(false, result)
    }

    @Test
    fun executeWithBlankNote_returnsTrueResult() {
        reset(noteRepository)
        whenever(noteRepository.delete(any())) doReturn TRUE

        val result = updateNoteUseCase.execute(BLANK_NOTE).blockingGet()

        assertEquals(true, result)
    }

    @Test
    fun executeWithBlankNote_returnsFalseResult() {
        reset(noteRepository)
        whenever(noteRepository.delete(any())) doReturn FALSE

        val result = updateNoteUseCase.execute(BLANK_NOTE).blockingGet()

        assertEquals(false, result)
    }

}
