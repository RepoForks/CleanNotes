package co.zsmb.cleannotes.presentation.notes

import co.zsmb.cleannotes.presentation.base.View

interface NotesView : View {

    fun displayNotes(notes: List<PresentableNote>)

}
