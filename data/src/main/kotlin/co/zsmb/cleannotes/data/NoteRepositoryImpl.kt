package co.zsmb.cleannotes.data

import co.zsmb.cleannotes.data.DomainNoteMapper.toDomainNote
import co.zsmb.cleannotes.data.DomainNoteMapper.toDomainNotes
import co.zsmb.cleannotes.data.DomainNoteMapper.toRealmNote
import co.zsmb.cleannotes.data.DomainNoteMapper.toRealmNotes
import co.zsmb.cleannotes.domain.DomainNote
import co.zsmb.cleannotes.domain.NoteRepository
import io.reactivex.Single
import javax.inject.Inject

class NoteRepositoryImpl @Inject constructor(private val noteDataSourceDisk: NoteDataSource)
    : NoteRepository {

    override fun add(note: DomainNote) = noteDataSourceDisk.add(note.toRealmNote())

    override fun addAll(notes: List<DomainNote>): Single<List<Int>> {
        val realmNotes = notes.toRealmNotes()
        return noteDataSourceDisk.addAll(realmNotes)
    }

    override fun delete(noteId: Int): Single<Boolean> = noteDataSourceDisk.delete(noteId)

    override fun deleteAll(noteIds: List<Int>) = noteDataSourceDisk.deleteAll(noteIds)

    override fun getAll(): Single<List<DomainNote>>
            = noteDataSourceDisk.getAll()
            .map { it.toDomainNotes() }

    override fun get(noteId: Int): Single<DomainNote>
            = noteDataSourceDisk.get(noteId)
            .map { it.toDomainNote() }

    override fun update(note: DomainNote): Single<Boolean> = noteDataSourceDisk.update(note.toRealmNote())

    override fun updateAll(notes: List<DomainNote>): Single<Int> {
        val realmNotes = notes.toRealmNotes()
        return noteDataSourceDisk.updateAll(realmNotes)
    }

}
