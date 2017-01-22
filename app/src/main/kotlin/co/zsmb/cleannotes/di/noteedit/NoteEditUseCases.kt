package co.zsmb.cleannotes.di.noteedit

import co.zsmb.cleannotes.domain.usecase.CreateNoteUseCase
import co.zsmb.cleannotes.domain.usecase.GetNoteUseCase
import co.zsmb.cleannotes.domain.usecase.UpdateNoteUseCase

interface NoteEditUseCases {

    fun getNoteUseCase(): GetNoteUseCase

    fun createNoteUseCase(): CreateNoteUseCase

    fun updateNoteUseCase(): UpdateNoteUseCase

}

class NoteEditUseCasesImpl(val createNoteUseCase: CreateNoteUseCase,
                           val getNoteUseCase: GetNoteUseCase,
                           val updateNoteUseCase: UpdateNoteUseCase)
    : NoteEditUseCases {

    override fun getNoteUseCase(): GetNoteUseCase = getNoteUseCase

    override fun createNoteUseCase(): CreateNoteUseCase = createNoteUseCase

    override fun updateNoteUseCase(): UpdateNoteUseCase = updateNoteUseCase

}
