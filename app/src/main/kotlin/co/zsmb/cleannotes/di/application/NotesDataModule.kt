package co.zsmb.cleannotes.di.application

import android.content.Context
import co.zsmb.cleannotes.data.NotesDataSource
import co.zsmb.cleannotes.data.NotesDataSourceDisk
import co.zsmb.cleannotes.data.NotesRepositoryImpl
import co.zsmb.cleannotes.domain.NotesRepository
import dagger.Module
import dagger.Provides
import io.realm.Realm
import javax.inject.Singleton

@Module
class NotesDataModule {

    @Provides @Singleton
    fun provideNotesRepository(notesDataSourceDisk: NotesDataSource): NotesRepository
            = NotesRepositoryImpl(notesDataSourceDisk)

    @Provides @Singleton
    fun provideNotesDataSource(context: Context): NotesDataSource {
        Realm.init(context)
        return NotesDataSourceDisk()
    }

}
