package co.zsmb.cleannotes.di.notedetails

import co.zsmb.cleannotes.di.application.ApplicationComponent
import co.zsmb.cleannotes.di.base.ActivityComponent
import co.zsmb.cleannotes.di.base.PerActivity
import co.zsmb.cleannotes.presentation.notedetails.NoteDetailsPresenter
import dagger.Component

@PerActivity
@Component(
        dependencies = arrayOf(ApplicationComponent::class),
        modules = arrayOf(
                NoteDetailsPresentationModule::class,
                NoteDetailsDomainModule::class
        )
)
interface NoteDetailsActivityComponent : ActivityComponent<NoteDetailsPresenter> {

    //fun makeNoteDetailsPresenter(): NoteDetailsPresenter

}
