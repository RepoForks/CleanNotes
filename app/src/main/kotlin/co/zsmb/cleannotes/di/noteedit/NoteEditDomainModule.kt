package co.zsmb.cleannotes.di.noteedit

import co.zsmb.cleannotes.domain.NoteRepository
import co.zsmb.cleannotes.domain.usecase.CreateNoteUseCase
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
                                updateNoteUseCase: UpdateNoteUseCase)
            : NoteEditUseCases
            = NoteEditUseCasesImpl(createNoteUseCase, getNoteUseCase, updateNoteUseCase)

    @Provides @PerActivity
    fun provideGetNoteUseCase(noteRepository: NoteRepository)
            = GetNoteUseCase(noteRepository, Schedulers.io())

    @Provides @PerActivity
    fun provideCreateNoteUseCase(noteRepository: NoteRepository)
            = CreateNoteUseCase(noteRepository, Schedulers.io())

    @Provides @PerActivity
    fun provideUpdateNoteUseCase(noteRepository: NoteRepository)
            = UpdateNoteUseCase(noteRepository, Schedulers.io())

}
