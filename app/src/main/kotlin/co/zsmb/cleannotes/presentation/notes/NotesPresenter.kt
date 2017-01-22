package co.zsmb.cleannotes.presentation.notes

import co.zsmb.cleannotes.presentation.base.Presenter

interface NotesPresenter : Presenter<NotesView> {

    fun loadNotes()

    fun openNote(note: PresentableNote)

    fun addTestNotes()

}
