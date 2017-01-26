package co.zsmb.cleannotes.presentation.notedetails

import co.zsmb.cleannotes.presentation.base.View

interface NoteDetailsView : View {

    fun displayNote(note: DetailedNote)

}
