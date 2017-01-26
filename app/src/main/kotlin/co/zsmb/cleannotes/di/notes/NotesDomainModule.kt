package co.zsmb.cleannotes.di.notes

import co.zsmb.cleannotes.domain.NotesRepository
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
    fun provideGetAllNotesUseCase(notesRepository: NotesRepository)
            = GetAllNotesUseCase(notesRepository, Schedulers.io())

}
