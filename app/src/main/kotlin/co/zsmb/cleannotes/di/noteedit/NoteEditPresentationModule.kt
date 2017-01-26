package co.zsmb.cleannotes.di.noteedit

import co.zsmb.cleannotes.presentation.base.PerActivity
import co.zsmb.cleannotes.presentation.noteedit.NoteEditPresenter
import co.zsmb.cleannotes.presentation.noteedit.NoteEditPresenterImpl
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers

@Module
class NoteEditPresentationModule {

    @Provides @PerActivity
    fun provideNoteEditPresenter(noteEditUseCases: NoteEditUseCases): NoteEditPresenter
            = NoteEditPresenterImpl(noteEditUseCases, AndroidSchedulers.mainThread())

}
