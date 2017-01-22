package co.zsmb.cleannotes.di.noteedit

import co.zsmb.cleannotes.di.base.PerActivity
import co.zsmb.cleannotes.domain.NotesRepository
import co.zsmb.cleannotes.domain.usecase.CreateNoteUseCase
import co.zsmb.cleannotes.domain.usecase.GetNoteUseCase
import co.zsmb.cleannotes.domain.usecase.UpdateNoteUseCase
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers

@Module
class NoteEditDomainModule {

    @Provides @PerActivity
    fun provideNoteEditUseCases(createNoteUseCase: CreateNoteUseCase,
                                getNoteUseCase: GetNoteUseCase,
                                updateNoteUseCase: UpdateNoteUseCase)
            : NoteEditUseCases
            = NoteEditUseCasesImpl(createNoteUseCase, getNoteUseCase, updateNoteUseCase)

    @Provides @PerActivity
    fun provideGetNoteUseCase(notesRepository: NotesRepository) = GetNoteUseCase(notesRepository, Schedulers.io())

    @Provides @PerActivity
    fun provideCreateNoteUseCase(notesRepository: NotesRepository) = CreateNoteUseCase(notesRepository, Schedulers.io())

    @Provides @PerActivity
    fun provideUpdateNoteUseCase(notesRepository: NotesRepository) = UpdateNoteUseCase(notesRepository, Schedulers.io())

}
