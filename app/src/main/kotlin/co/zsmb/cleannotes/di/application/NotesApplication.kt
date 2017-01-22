package co.zsmb.cleannotes.di.application

import android.app.Application

class NotesApplication : Application() {

    override fun onCreate() {
        super.onCreate()

        applicationComponent = DaggerApplicationComponent.builder()
                .applicationModule(ApplicationModule(this))
                .build()
    }

    companion object {
        lateinit var applicationComponent: ApplicationComponent
    }

}
