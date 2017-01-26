package co.zsmb.cleannotes.presentation.noteedit

import co.zsmb.cleannotes.di.noteedit.NoteEditUseCases
import co.zsmb.cleannotes.domain.DomainNote
import co.zsmb.cleannotes.domain.usecase.CreateNoteUseCase
import co.zsmb.cleannotes.domain.usecase.DeleteNoteUseCase
import co.zsmb.cleannotes.domain.usecase.GetNoteUseCase
import co.zsmb.cleannotes.domain.usecase.UpdateNoteUseCase
import com.nhaarman.mockito_kotlin.any
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito.verify
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NoteEditPresenterImplTest {

    @Mock lateinit var createNoteUseCase: CreateNoteUseCase
    @Mock lateinit var getNoteUseCase: GetNoteUseCase
    @Mock lateinit var updateNoteUseCase: UpdateNoteUseCase
    @Mock lateinit var deleteNoteUseCase: DeleteNoteUseCase

    @Mock lateinit var noteEditUseCases: NoteEditUseCases

    @Mock lateinit var view: NoteEditView

    lateinit var noteEditPresenter: NoteEditPresenter

    @Before
    fun setUp() {
        whenever(noteEditUseCases.createNoteUseCase()) doReturn createNoteUseCase
        whenever(noteEditUseCases.getNoteUseCase()) doReturn getNoteUseCase
        whenever(noteEditUseCases.updateNoteUseCase()) doReturn updateNoteUseCase

        noteEditPresenter = NoteEditPresenterImpl(noteEditUseCases, Schedulers.trampoline())
        noteEditPresenter.bind(view)
    }

    @Test
    fun createNote_executesCreateNoteUseCase() {
        whenever(createNoteUseCase.execute(Unit)) doReturn Single.just(DomainNote(0, "", "", 0))

        noteEditPresenter.createNote()

        verify(createNoteUseCase).execute(Unit)
    }

    @Test
    fun loadNote_executesGetNoteUseCaseWithParam() {
        whenever(getNoteUseCase.execute(1)) doReturn Single.just(DomainNote(0, "", "", 0))

        noteEditPresenter.loadNote(1)

        verify(getNoteUseCase).execute(1)
    }

    @Test
    fun loadNote_executesGetNoteUseCaseWithParam2() {
        whenever(getNoteUseCase.execute(5)) doReturn Single.just(DomainNote(0, "", "", 0))

        noteEditPresenter.loadNote(5)

        verify(getNoteUseCase).execute(5)
    }

    @Test
    fun loadNote_callsViewDisplayNote() {
        whenever(getNoteUseCase.execute(1)) doReturn Single.just(DomainNote(0, "", "", 0))

        noteEditPresenter.loadNote(1)

        verify(view).displayNote(any())
    }

    @Test
    fun loadNote_callsViewWithParam() {
        whenever(getNoteUseCase.execute(1)) doReturn Single.just(DomainNote(123, "title", "content", 1))

        noteEditPresenter.loadNote(1)

        verify(view).displayNote(EditableNote(123, "title", "content"))
    }

    @Test
    fun saveNote_executesUpdateNoteUseCaseWithRightParams() {
        whenever(updateNoteUseCase.execute(any())) doReturn Single.just(true)

        noteEditPresenter.saveNote(EditableNote(123, "title", "content"))

        verify(updateNoteUseCase).execute(DomainNote(123, "title", "content", 0))
    }

}
