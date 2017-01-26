package co.zsmb.cleannotes.di.notes

import co.zsmb.cleannotes.domain.usecase.GetAllNotesUseCase

interface NotesUseCases {

    fun getAllNotesUseCase(): GetAllNotesUseCase

}

class NotesUseCasesImpl(val getAllNotesUseCase: GetAllNotesUseCase) : NotesUseCases {

    override fun getAllNotesUseCase(): GetAllNotesUseCase = getAllNotesUseCase

}


