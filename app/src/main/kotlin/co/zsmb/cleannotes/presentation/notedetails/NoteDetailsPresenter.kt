package co.zsmb.cleannotes.presentation.notedetails

import co.zsmb.cleannotes.presentation.base.Presenter

interface NoteDetailsPresenter : Presenter<NoteDetailsView> {

    fun loadNote(id: Int)

    fun editNote(noteId: Int)

}
