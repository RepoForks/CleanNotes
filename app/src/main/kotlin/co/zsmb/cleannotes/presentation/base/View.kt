package co.zsmb.cleannotes.presentation.base

interface View {

    fun close()

    fun showMessage(message: String)

    fun showError(error: String)

}
