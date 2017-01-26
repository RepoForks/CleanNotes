package co.zsmb.cleannotes.di.notedetails

import co.zsmb.cleannotes.domain.NoteRepository
import co.zsmb.cleannotes.domain.usecase.DeleteNoteUseCase
import co.zsmb.cleannotes.domain.usecase.GetNoteUseCase
import co.zsmb.cleannotes.presentation.base.PerActivity
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers

@Module
class NoteDetailsDomainModule {

    @Provides @PerActivity
    fun provideGetNoteUseCase(noteRepository: NoteRepository): GetNoteUseCase
            = GetNoteUseCase(noteRepository, Schedulers.io())

    @Provides @PerActivity
    fun provideDeleteNoteUseCase(noteRepository: NoteRepository): DeleteNoteUseCase
            = DeleteNoteUseCase(noteRepository, Schedulers.io())

}
