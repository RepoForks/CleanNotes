package co.zsmb.cleannotes.di.notedetails

import co.zsmb.cleannotes.di.base.PerActivity
import co.zsmb.cleannotes.domain.usecase.GetNoteUseCase
import co.zsmb.cleannotes.presentation.base.Navigator
import co.zsmb.cleannotes.presentation.notedetails.NoteDetailsPresenter
import co.zsmb.cleannotes.presentation.notedetails.NoteDetailsPresenterImpl
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers

@Module
class NoteDetailsPresentationModule {

    @Provides @PerActivity
    fun provideNoteDetailsPresenter(navigator: Navigator,
                                    getNoteUseCase: GetNoteUseCase)
            : NoteDetailsPresenter
            = NoteDetailsPresenterImpl(navigator, getNoteUseCase, AndroidSchedulers.mainThread())

}
