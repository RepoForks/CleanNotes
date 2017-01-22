package co.zsmb.cleannotes.di.noteedit

import co.zsmb.cleannotes.di.application.ApplicationComponent
import co.zsmb.cleannotes.di.base.ActivityComponent
import co.zsmb.cleannotes.di.base.PerActivity
import co.zsmb.cleannotes.presentation.noteedit.NoteEditPresenter
import dagger.Component

@PerActivity
@Component(
        dependencies = arrayOf(ApplicationComponent::class),
        modules = arrayOf(
                NoteEditPresentationModule::class,
                NoteEditDomainModule::class
        )
)
interface NoteEditActivityComponent : ActivityComponent<NoteEditPresenter>, NoteEditUseCases
