package co.zsmb.cleannotes.presentation.notes

import co.zsmb.cleannotes.di.notes.NotesUseCases
import co.zsmb.cleannotes.domain.DomainNote
import co.zsmb.cleannotes.domain.usecase.GetAllNotesUseCase
import co.zsmb.cleannotes.presentation.base.Navigator
import com.nhaarman.mockito_kotlin.capture
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
import org.mockito.ArgumentCaptor
import org.mockito.Captor
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule


@RunWith(Parameterized::class)
class NotesPresenterImplParameterizedTest(val testParam: List<DomainNote>) {

    @get: Rule val rule: MockitoRule = MockitoJUnit.rule()

    @Mock lateinit var getAllNotesUseCase: GetAllNotesUseCase
    @Mock lateinit var notesUseCases: NotesUseCases
    @Mock lateinit var navigator: Navigator

    @Mock lateinit var view: NotesView

    @Captor lateinit var captor: ArgumentCaptor<List<PresentableNote>>

    lateinit var notesPresenter: NotesPresenter

    @Before
    fun setUp() {
        whenever(getAllNotesUseCase.execute(Unit)) doReturn Single.just(testParam)
        whenever(notesUseCases.getAllNotesUseCase()) doReturn getAllNotesUseCase

        notesPresenter = NotesPresenterImpl(navigator, notesUseCases, Schedulers.trampoline())
        notesPresenter.bind(view)
    }

    @Test
    fun loadNotes_displaysAsManyNotesAsUseCaseReturns() {
        notesPresenter.loadNotes()

        verify(view).displayNotes(capture(captor))

        val capturedList = captor.value

        assertEquals(testParam.size, capturedList.size)
    }

    @Test
    fun loadNotes_displaysNotesWithSameIdsAsUseCaseReturns() {
        notesPresenter.loadNotes()

        verify(view).displayNotes(capture(captor))

        val displayedNotes = captor.value

        val originalIds = testParam.map { it.id }.toSet()
        val displayedNoteIds = displayedNotes.map { it.id }.toSet()

        assertEquals(originalIds, displayedNoteIds)
    }

    @Test
    fun loadNotes_displaysNotesWithUniqueIds() {
        notesPresenter.loadNotes()

        verify(view).displayNotes(capture(captor))

        val displayedNotes = captor.value

        val displayedNoteIds = displayedNotes.map { it.id }.toSet()

        // IDs are unique if their set size matches the number of notes
        assertEquals(testParam.size, displayedNoteIds.size)
    }

    @Test
    fun loadNotes_sortsNotesByTimestampDescending() {
        notesPresenter.loadNotes()

        verify(view).displayNotes(capture(captor))

        val displayedNotes = captor.value
        val sortedNotes = testParam.sortedByDescending { it.timestamp }

        // Check that the two lists are in the same order
        for (i in displayedNotes.indices) {
            assertEquals(displayedNotes[i].id, sortedNotes[i].id)
        }
    }

    companion object {

        @Parameterized.Parameters @JvmStatic
        fun data() = listOf(
                listOf<DomainNote>(),
                listOf(
                        DomainNote(1, "First element", "First content", 1)
                ),
                listOf(
                        DomainNote(1, "First element", "First content", 20),
                        DomainNote(2, "Second element", "Second content", 5),
                        DomainNote(3, "Third element", "Third content", 7)
                ),
                listOf(
                        DomainNote(1, "First element", "First content", 5),
                        DomainNote(2, "Second element", "Second content", 45548),
                        DomainNote(3, "Third element", "Third content", 3),
                        DomainNote(4, "Fourth element", "Fourth content", 195),
                        DomainNote(5, "Fifth element", "Fifth content", 894)
                )
        )

    }

}
