package co.zsmb.cleannotes.presentation.notedetails

import co.zsmb.cleannotes.di.notedetails.NoteDetailsUseCases
import co.zsmb.cleannotes.domain.DomainNote
import co.zsmb.cleannotes.domain.usecase.DeleteNoteUseCase
import co.zsmb.cleannotes.domain.usecase.GetNoteUseCase
import co.zsmb.cleannotes.presentation.base.Navigator
import co.zsmb.cleannotes.presentation.noteedit.NoteEditActivity
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import io.reactivex.schedulers.Schedulers
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized
import org.mockito.Mock
import org.mockito.junit.MockitoJUnit
import org.mockito.junit.MockitoRule

@RunWith(Parameterized::class)
class NoteDetailsPresenterImplParameterizedTest(val testId: Int) {

    @get: Rule val rule: MockitoRule = MockitoJUnit.rule()

    @Mock lateinit var navigator: Navigator
    @Mock lateinit var getNoteUseCase: GetNoteUseCase
    @Mock lateinit var deleteNoteUseCase: DeleteNoteUseCase
    @Mock lateinit var noteDetailsUseCases: NoteDetailsUseCases

    @Mock lateinit var view: NoteDetailsView

    lateinit var noteDetailsPresenter: NoteDetailsPresenter

    @Before
    fun setUp() {
        noteDetailsPresenter = NoteDetailsPresenterImpl(navigator, noteDetailsUseCases, Schedulers.trampoline())
        noteDetailsPresenter.bind(view)
    }

    @Test
    fun editNote_navigatesToNodeEditActivityWithId() {
        noteDetailsPresenter.editNote(testId)

        verify(navigator).goto(NoteEditActivity::class, "id" to testId)
    }

    @Test
    fun loadNote_executesUseCaseWithId() {
        whenever(noteDetailsUseCases.getNoteUseCase()) doReturn getNoteUseCase
        whenever(getNoteUseCase.execute(testId)) doReturn Single.just(DomainNote(testId, "", "", 0))

        noteDetailsPresenter.loadNote(testId)

        verify(getNoteUseCase).execute(testId)
    }

    companion object {

        @Parameterized.Parameters @JvmStatic
        fun data() = listOf(-99999, -10, -5, -2, -1, 0, 1, 2, 5, 10, 99999)

    }

}

