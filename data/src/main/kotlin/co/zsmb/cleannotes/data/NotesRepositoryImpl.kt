package co.zsmb.cleannotes.data

import co.zsmb.cleannotes.domain.DomainNote
import co.zsmb.cleannotes.domain.NotesRepository
import io.reactivex.Observable
import javax.inject.Inject

class NotesRepositoryImpl @Inject constructor(val notesDataSourceDisk: NotesDataSource)
    : NotesRepository {

    private val realmToDomain: (RealmNote) -> DomainNote = { DomainNote(it.id, it.title, it.content) }
    private val domainToRealm: (DomainNote) -> RealmNote = { RealmNote(it.id, it.title, it.content) }

    override fun getAll(): Observable<List<DomainNote>>
            = notesDataSourceDisk.getAll()
            .map { it.map(realmToDomain) }

    override fun get(id: Int): Observable<DomainNote>
            = notesDataSourceDisk.get(id)
            .map(realmToDomain)

    override fun add(note: DomainNote) = notesDataSourceDisk.add(domainToRealm(note))

    override fun addAll(notes: List<DomainNote>): Observable<Int> {
        val realmNotes = notes.map(domainToRealm)
        return notesDataSourceDisk.addAll(realmNotes)
    }

    override fun update(note: DomainNote): Observable<Int> = notesDataSourceDisk.update(domainToRealm(note))

    override fun updateAll(notes: List<DomainNote>): Observable<Int> {
        val realmNotes = notes.map(domainToRealm)
        return notesDataSourceDisk.updateAll(realmNotes)
    }

}
