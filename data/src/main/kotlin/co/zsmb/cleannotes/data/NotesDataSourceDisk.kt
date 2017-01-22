package co.zsmb.cleannotes.data

import io.reactivex.Observable
import io.realm.Realm
import javax.inject.Inject

class NotesDataSourceDisk @Inject constructor(val realm: Realm)
    : NotesDataSource {

    override fun getAll(): Observable<List<RealmNote>> {
        val notes = realm.where(RealmNote::class.java).findAll().map {
            realm.copyFromRealm(it)
        }
        return Observable.just(notes)
    }

    override fun get(id: Int): Observable<RealmNote> {
        val note = realm.where(RealmNote::class.java).equalTo("id", id).findFirst()

        if (note == null) {
            return Observable.error(RuntimeException("No note exists with id $id"))
        }
        else {
            return Observable.just(realm.copyFromRealm(note))
        }
    }

    override fun add(note: RealmNote): Observable<Int> {
        realm.executeTransaction {
            note.giveId(it)
            it.insert(note)
        }
        return Observable.just(note.id)
    }

    override fun addAll(notes: List<RealmNote>): Observable<Int> {
        // TODO check for failure somehow
        realm.executeTransaction {
            notes.giveIds(it)
            it.insert(notes)
        }
        return Observable.just(notes.size)
    }

    override fun update(note: RealmNote): Observable<Int> {
        // TODO check for failure somehow
        realm.executeTransaction {
            it.insertOrUpdate(note)
        }
        return Observable.just(1)
    }

    override fun updateAll(notes: List<RealmNote>): Observable<Int> {
        // TODO check for failure somehow
        realm.executeTransaction {
            it.insertOrUpdate(notes)
        }
        return Observable.just(notes.size)
    }

    private fun createNewId(realm: Realm): Int {
        val maxId = realm.where(RealmNote::class.java).max("id") ?: return 0
        return maxId.toInt() + 1
    }

    private fun RealmNote.giveId(realm: Realm) {
        this.id = createNewId(realm)
    }

    private fun Iterable<RealmNote>.giveIds(realm: Realm) {
        var generatedId = createNewId(realm)
        forEach {
            it.id = generatedId++
        }
    }

}

