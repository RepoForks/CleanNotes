package co.zsmb.cleannotes.presentation.notes

interface NotesView {

    fun displayNotes(notes: List<PresentableNote>)

    fun showMessage(message: String)

}