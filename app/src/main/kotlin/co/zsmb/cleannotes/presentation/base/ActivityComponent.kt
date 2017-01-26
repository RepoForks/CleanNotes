package co.zsmb.cleannotes.presentation.base

interface ActivityComponent<out PR> {

    fun createPresenter(): PR

}
