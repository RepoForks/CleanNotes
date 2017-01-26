package co.zsmb.cleannotes.presentation.notedetails

import co.zsmb.cleannotes.di.notedetails.NoteDetailsUseCases
import co.zsmb.cleannotes.domain.DomainNote
import co.zsmb.cleannotes.domain.usecase.DeleteNoteUseCase
import co.zsmb.cleannotes.domain.usecase.GetNoteUseCase
import co.zsmb.cleannotes.presentation.base.Navigator
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
class NoteDetailsPresenterImplTest {

    @Mock lateinit var navigator: Navigator
    @Mock lateinit var getNoteUseCase: GetNoteUseCase
    @Mock lateinit var deleteNoteUseCase: DeleteNoteUseCase
    @Mock lateinit var noteDetailsUseCases: NoteDetailsUseCases

    @Mock lateinit var view: NoteDetailsView

    lateinit var noteDetailsPresenter: NoteDetailsPresenter

    @Before
    fun setUp() {
        whenever(noteDetailsUseCases.getNoteUseCase()) doReturn getNoteUseCase

        val note = DomainNote(1, "First", "First content", 1485449659000L)
        whenever(getNoteUseCase.execute(1)) doReturn Single.just(note)

        noteDetailsPresenter = NoteDetailsPresenterImpl(navigator, noteDetailsUseCases, Schedulers.trampoline())
        noteDetailsPresenter.bind(view)
    }

    @Test
    fun loadNote_callsViewDisplayNote() {
        noteDetailsPresenter.loadNote(1)

        verify(view).displayNote(any())
    }

    @Test
    fun loadNote_transformsNoteProperly() {
        noteDetailsPresenter.loadNote(1)

        verify(view).displayNote(DetailedNote(1, "First", "First content", "2017-01-26 17:54"))
    }

    @Test
    fun loadNote_closesActivityIfNoteIsEmpty() {
        whenever(getNoteUseCase.execute(2)) doReturn Single.just(DomainNote(2, "", "", 0))

        noteDetailsPresenter.loadNote(2)

        verify(view).close()
    }

    @Test
    fun loadNote_closesActivityIfNoteIsBlank() {
        whenever(getNoteUseCase.execute(2)) doReturn Single.just(DomainNote(2, "    ", "             ", 0))

        noteDetailsPresenter.loadNote(2)

        verify(view).close()
    }

}
