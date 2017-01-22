package co.zsmb.cleannotes.di.base

interface ActivityComponent<out PR> {

    fun createPresenter(): PR

}
