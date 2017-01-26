package co.zsmb.cleannotes.di.notes

import co.zsmb.cleannotes.domain.NoteRepository
import co.zsmb.cleannotes.domain.usecase.GetAllNotesUseCase
import co.zsmb.cleannotes.presentation.base.PerActivity
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers

@Module
class NotesDomainModule {

    @Provides @PerActivity
    fun provideNotesUseCases(getAllNotesUseCase: GetAllNotesUseCase)
            : NotesUseCases
            = NotesUseCasesImpl(getAllNotesUseCase)

    @Provides @PerActivity
    fun provideGetAllNotesUseCase(noteRepository: NoteRepository)
            = GetAllNotesUseCase(noteRepository, Schedulers.io())

}
