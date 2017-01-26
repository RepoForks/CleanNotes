package co.zsmb.cleannotes.presentation.notedetails

import co.zsmb.cleannotes.presentation.base.Presenter

interface NoteDetailsPresenter : Presenter<NoteDetailsView> {

    fun deleteNote(noteId: Int)

    fun editNote(noteId: Int)

    fun loadNote(id: Int)

}
