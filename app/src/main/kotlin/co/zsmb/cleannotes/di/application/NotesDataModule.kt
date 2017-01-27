package co.zsmb.cleannotes.di.application

import android.content.Context
import co.zsmb.cleannotes.data.NoteDataSource
import co.zsmb.cleannotes.data.NoteDataSourceDisk
import co.zsmb.cleannotes.data.NoteRepositoryImpl
import co.zsmb.cleannotes.domain.NoteRepository
import dagger.Module
import dagger.Provides
import io.realm.Realm
import javax.inject.Singleton

@Module
class NotesDataModule {

    @Provides @Singleton
    fun provideNotesRepository(noteDataSourceDisk: NoteDataSource): NoteRepository
            = NoteRepositoryImpl(noteDataSourceDisk)

    @Provides @Singleton
    fun provideNotesDataSource(context: Context): NoteDataSource {
        Realm.init(context)
        return NoteDataSourceDisk()
    }

}
