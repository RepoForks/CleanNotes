package co.zsmb.cleannotes.di.notes

import co.zsmb.cleannotes.di.application.ApplicationComponent
import co.zsmb.cleannotes.di.base.ActivityComponent
import co.zsmb.cleannotes.di.base.PerActivity
import co.zsmb.cleannotes.presentation.notes.NotesPresenter
import dagger.Component

@PerActivity
@Component(
        dependencies = arrayOf(ApplicationComponent::class),
        modules = arrayOf(
                NotesPresentationModule::class,
                NotesDomainModule::class
        ))
interface NotesActivityComponent : ActivityComponent<NotesPresenter> {

    //fun makeNotesPresenter(): NotesPresenter

}
