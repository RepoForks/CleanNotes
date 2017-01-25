package co.zsmb.cleannotes.di.notedetails

import co.zsmb.cleannotes.domain.usecase.DeleteNoteUseCase
import co.zsmb.cleannotes.domain.usecase.GetNoteUseCase

interface NoteDetailsUseCases {

    fun getNoteUseCase(): GetNoteUseCase

    fun deleteNoteUseCase(): DeleteNoteUseCase

}

class NoteDetailsUseCasesImpl(private val getNoteUseCase: GetNoteUseCase,
                              private val deleteNoteUseCase: DeleteNoteUseCase)
    : NoteDetailsUseCases {

    override fun getNoteUseCase(): GetNoteUseCase = getNoteUseCase

    override fun deleteNoteUseCase(): DeleteNoteUseCase = deleteNoteUseCase

}
