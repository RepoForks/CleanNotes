package co.zsmb.cleannotes.presentation.notes

import co.zsmb.cleannotes.di.notes.NotesUseCases
import co.zsmb.cleannotes.domain.DomainNote
import co.zsmb.cleannotes.domain.usecase.GetAllNotesUseCase
import co.zsmb.cleannotes.presentation.base.Navigator
import co.zsmb.cleannotes.presentation.notedetails.NoteDetailsActivity
import co.zsmb.cleannotes.presentation.noteedit.NoteEditActivity
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NotesPresenterImplTest {

    @Mock lateinit var getAllNotesUseCase: GetAllNotesUseCase
    @Mock lateinit var notesUseCases: NotesUseCases
    @Mock lateinit var navigator: Navigator

    @Mock lateinit var view: NotesView

    lateinit var notesPresenter: NotesPresenter

    @Before
    fun setUp() {
        whenever(notesUseCases.getAllNotesUseCase()) doReturn getAllNotesUseCase

        notesPresenter = NotesPresenterImpl(navigator, notesUseCases, Schedulers.trampoline())
        notesPresenter.bind(view)
    }

    @Test
    fun openNote_navigatesToNoteDetailsActivityWithId() {
        notesPresenter.openNote(PresentableNote(15, "", ""))

        verify(navigator).goto(NoteDetailsActivity::class, "id" to 15)
    }

    @Test
    fun createNote_navigatesToNoteEditActivity() {
        notesPresenter.createNote()

        verify(navigator).goto(NoteEditActivity::class)
    }

    @Test
    fun loadNotes_executesGetAllNotesUseCase() {
        whenever(getAllNotesUseCase.execute(Unit)) doReturn Single.just(listOf<DomainNote>())

        notesPresenter.loadNotes()

        verify(getAllNotesUseCase).execute(Unit)
    }

    @Test
    fun loadNotes_callsDisplayNotes() {
        whenever(getAllNotesUseCase.execute(Unit)) doReturn Single.just(listOf<DomainNote>())

        notesPresenter.loadNotes()

        verify(view).displayNotes(any())
    }

    @Test
    fun loadNotes_transformsNotesProperly() {
        whenever(getAllNotesUseCase.execute(Unit)) doReturn Single.just(listOf(DomainNote(12, "title", "content", 25)))

        notesPresenter.loadNotes()

        verify(view).displayNotes(listOf(PresentableNote(12, "title", "content")))
    }

}
