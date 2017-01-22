package co.zsmb.cleannotes.di.application

import android.content.Context
import co.zsmb.cleannotes.domain.NotesRepository
import co.zsmb.cleannotes.presentation.base.Navigator
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
        modules = arrayOf(
                ApplicationModule::class,
                NotesDataModule::class
        ))
interface ApplicationComponent {

    fun context(): Context

    fun navigator(): Navigator

    fun notesRepository(): NotesRepository

}
