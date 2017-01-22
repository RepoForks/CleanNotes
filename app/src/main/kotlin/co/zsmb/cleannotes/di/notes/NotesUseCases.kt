package co.zsmb.cleannotes.di.notes

import co.zsmb.cleannotes.domain.usecase.CreateTestNotesUseCase
import co.zsmb.cleannotes.domain.usecase.GetAllNotesUseCase

interface NotesUseCases {

    fun getAllNotesUseCase(): GetAllNotesUseCase

    fun createTestNotesUseCase(): CreateTestNotesUseCase

}

class NotesUseCasesImpl(val getAllNotesUseCase: GetAllNotesUseCase,
                        val createTestNotesUseCase: CreateTestNotesUseCase)
    : NotesUseCases {

    override fun getAllNotesUseCase(): GetAllNotesUseCase = getAllNotesUseCase

    override fun createTestNotesUseCase(): CreateTestNotesUseCase = createTestNotesUseCase

}


