package co.zsmb.cleannotes.di.notedetails

import co.zsmb.cleannotes.di.base.PerActivity
import co.zsmb.cleannotes.domain.NotesRepository
import co.zsmb.cleannotes.domain.usecase.DeleteNoteUseCase
import co.zsmb.cleannotes.domain.usecase.GetNoteUseCase
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers

@Module
class NoteDetailsDomainModule {

    @Provides @PerActivity
    fun provideGetNoteUseCase(notesRepository: NotesRepository): GetNoteUseCase
            = GetNoteUseCase(notesRepository, Schedulers.io())

    @Provides @PerActivity
    fun provideDeleteNoteUseCase(notesRepository: NotesRepository): DeleteNoteUseCase
            = DeleteNoteUseCase(notesRepository, Schedulers.io())

}
