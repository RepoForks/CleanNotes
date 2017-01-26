package co.zsmb.cleannotes.data

import co.zsmb.cleannotes.domain.DomainNote
import co.zsmb.cleannotes.domain.NotesRepository
import io.reactivex.Single
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(private val notesDataSourceDisk: NotesDataSource)
    : NotesRepository {

    private val realmToDomain: (RealmNote) -> DomainNote = { DomainNote(it.id, it.title, it.content) }
    private val domainToRealm: (DomainNote) -> RealmNote = { RealmNote(it.id, it.title, it.content) }

    override fun add(note: DomainNote) = notesDataSourceDisk.add(domainToRealm(note))

    override fun addAll(notes: List<DomainNote>): Single<List<Int>> {
        val realmNotes = notes.map(domainToRealm)
        return notesDataSourceDisk.addAll(realmNotes)
    }

    override fun delete(noteId: Int): Single<Boolean> = notesDataSourceDisk.delete(noteId)

    override fun deleteAll(noteIds: List<Int>) = notesDataSourceDisk.deleteAll(noteIds)

    override fun getAll(): Single<List<DomainNote>>
            = notesDataSourceDisk.getAll()
            .map { it.map(realmToDomain) }

    override fun get(noteId: Int): Single<DomainNote>
            = notesDataSourceDisk.get(noteId)
            .map(realmToDomain)

    override fun update(note: DomainNote): Single<Boolean> = notesDataSourceDisk.update(domainToRealm(note))

    override fun updateAll(notes: List<DomainNote>): Single<Int> {
        val realmNotes = notes.map(domainToRealm)
        return notesDataSourceDisk.updateAll(realmNotes)
    }

}
