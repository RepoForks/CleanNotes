package co.zsmb.cleannotes.di.notes

import co.zsmb.cleannotes.presentation.base.Navigator
import co.zsmb.cleannotes.presentation.base.PerActivity
import co.zsmb.cleannotes.presentation.notes.NotesPresenter
import co.zsmb.cleannotes.presentation.notes.NotesPresenterImpl
import dagger.Module
import dagger.Provides
import io.reactivex.android.schedulers.AndroidSchedulers

@Module
class NotesPresentationModule {

    @Provides @PerActivity
    fun provideNotesPresenter(navigator: Navigator,
                              notesUseCases: NotesUseCases)
            : NotesPresenter
            = NotesPresenterImpl(navigator, notesUseCases, AndroidSchedulers.mainThread())

}
