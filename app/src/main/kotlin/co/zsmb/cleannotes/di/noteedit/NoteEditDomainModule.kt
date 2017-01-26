package co.zsmb.cleannotes.di.noteedit

import co.zsmb.cleannotes.domain.NotesRepository
import co.zsmb.cleannotes.domain.usecase.CreateNoteUseCase
import co.zsmb.cleannotes.domain.usecase.DeleteNoteUseCase
import co.zsmb.cleannotes.domain.usecase.GetNoteUseCase
import co.zsmb.cleannotes.domain.usecase.UpdateNoteUseCase
import co.zsmb.cleannotes.presentation.base.PerActivity
import dagger.Module
import dagger.Provides
import io.reactivex.schedulers.Schedulers

@Module
class NoteEditDomainModule {

    @Provides @PerActivity
    fun provideNoteEditUseCases(createNoteUseCase: CreateNoteUseCase,
                                getNoteUseCase: GetNoteUseCase,
                                updateNoteUseCase: UpdateNoteUseCase,
                                deleteNoteUseCase: DeleteNoteUseCase)
            : NoteEditUseCases
            = NoteEditUseCasesImpl(createNoteUseCase, getNoteUseCase, updateNoteUseCase, deleteNoteUseCase)

    @Provides @PerActivity
    fun provideGetNoteUseCase(notesRepository: NotesRepository) = GetNoteUseCase(notesRepository, Schedulers.io())

    @Provides @PerActivity
    fun provideCreateNoteUseCase(notesRepository: NotesRepository) = CreateNoteUseCase(notesRepository, Schedulers.io())

    @Provides @PerActivity
    fun provideUpdateNoteUseCase(notesRepository: NotesRepository) = UpdateNoteUseCase(notesRepository, Schedulers.io())

    @Provides @PerActivity
    fun provideDeleteNoteUseCase(notesRepository: NotesRepository) = DeleteNoteUseCase(notesRepository, Schedulers.io())

}
