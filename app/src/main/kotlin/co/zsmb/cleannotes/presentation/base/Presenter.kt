package co.zsmb.cleannotes.presentation.base

interface Presenter<in V : Any> : Terminable {

    fun bind(view: V)

    fun unbind()

}
