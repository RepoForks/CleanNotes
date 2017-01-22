package co.zsmb.cleannotes.di.notes

import co.zsmb.cleannotes.di.base.PerActivity
import co.zsmb.cleannotes.domain.NotesRepository
import co.zsmb.cleannotes.domain.usecase.CreateTestNotesUseCase
import co.zsmb.cleannotes.domain.usecase.GetAllNotesUseCase
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers

@Module
class NotesDomainModule {

    @Provides @PerActivity
    fun provideNotesUseCases(getAllNotesUseCase: GetAllNotesUseCase,
                             createTestNotesUseCase: CreateTestNotesUseCase)
            : NotesUseCases
            = NotesUseCasesImpl(getAllNotesUseCase, createTestNotesUseCase)

    @Provides @PerActivity
    fun provideGetAllNotesUseCase(notesRepository: NotesRepository): GetAllNotesUseCase
            = GetAllNotesUseCase(notesRepository, Schedulers.io())

    @Provides @PerActivity
    fun provideCreateTestNotesUseCase(notesRepository: NotesRepository): CreateTestNotesUseCase
            = CreateTestNotesUseCase(notesRepository, Schedulers.io())

}
