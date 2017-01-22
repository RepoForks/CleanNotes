package co.zsmb.cleannotes.presentation.noteedit

import co.zsmb.cleannotes.presentation.base.Presenter

interface NoteEditPresenter : Presenter<NoteEditView> {

    fun createNote()

    fun loadNote(id: Int)

    fun saveNote(note: EditableNote)

}
