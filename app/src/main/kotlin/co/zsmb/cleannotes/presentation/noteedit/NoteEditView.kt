package co.zsmb.cleannotes.presentation.noteedit

import co.zsmb.cleannotes.presentation.base.View

interface NoteEditView : View {

    fun displayNote(note: EditableNote)

}
