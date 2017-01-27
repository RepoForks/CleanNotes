package co.zsmb.cleannotes.data

import co.zsmb.cleannotes.data.DomainNoteMapper.toRealmNote
import co.zsmb.cleannotes.data.DomainNoteMapper.toRealmNotes
import co.zsmb.cleannotes.domain.DomainNote
import co.zsmb.cleannotes.domain.NoteRepository
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.reset
import com.nhaarman.mockito_kotlin.verify
import com.nhaarman.mockito_kotlin.whenever
import io.reactivex.Single
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class NoteRepositoryImplTest {

    @Mock lateinit var noteDataSource: NoteDataSource
    lateinit var noteRepositoryImpl: NoteRepository

    val domainNotes = listOf(
            DomainNote(1, "title1", "content1", 1),
            DomainNote(2, "title2", "content2", 2),
            DomainNote(3, "title3", "content3", 3)
    )

    val realmNotes = domainNotes.toRealmNotes()

    @Before
    fun setUp() {
        noteRepositoryImpl = NoteRepositoryImpl(noteDataSource)
    }

    @Test
    fun add_transformsAndAddsToDataSource() {
        domainNotes.forEach {
            noteRepositoryImpl.add(it)

            verify(noteDataSource).add(it.toRealmNote())

            reset(noteDataSource)
        }
    }

    @Test
    fun addAll_transformsAndAddsToDataSource() {
        noteRepositoryImpl.addAll(domainNotes)

        verify(noteDataSource).addAll(domainNotes.toRealmNotes())
    }

    @Test
    fun delete_deletesFromDataSource() {
        val ids = listOf(-8, -1, 0, 1, 2, 3, 5, 10, 100, 400, 3156)

        ids.forEach {
            noteRepositoryImpl.delete(it)

            verify(noteDataSource).delete(it)

            reset(noteDataSource)
        }
    }

    @Test
    fun deleteAll_deletesAllFromDataSource() {
        val ids = listOf(1, 2, 3, 4, 5)

        noteRepositoryImpl.deleteAll(ids)

        verify(noteDataSource).deleteAll(ids)
    }

    @Test
    fun get_getsFromDataSourceAndTransforms() {
        realmNotes.indices.forEach {
            whenever(noteDataSource.get(it)) doReturn Single.just(realmNotes[it])
        }

        realmNotes.indices.forEach {
            val result = noteRepositoryImpl.get(it).blockingGet()

            assertEquals(domainNotes[it], result)
        }
    }

    @Test
    fun getAll_getsFromDataSourceAndTransforms() {
        whenever(noteDataSource.getAll()) doReturn Single.just(realmNotes)

        val result = noteRepositoryImpl.getAll().blockingGet()

        assertEquals(domainNotes, result)
    }

    @Test
    fun update_transformsAndUpdatesDataSource() {
        domainNotes.forEach {
            noteRepositoryImpl.update(it)

            verify(noteDataSource).update(it.toRealmNote())

            reset(noteDataSource)
        }
    }

    @Test
    fun updateAll_transformsAndUpdatesDataSource() {
        noteRepositoryImpl.updateAll(domainNotes)

        verify(noteDataSource).updateAll(domainNotes.toRealmNotes())
    }

}