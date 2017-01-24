package co.zsmb.cleannotes.data

import io.reactivex.Observable
import io.realm.Realm

class NotesDataSourceDisk : NotesDataSource {

    override fun getAll(): Observable<List<RealmNote>> {
        val notes = withRealm {
            where(RealmNote::class.java).findAll().map { copyFromRealm(it) }
        }
        return Observable.just(notes)
    }

    override fun get(id: Int): Observable<RealmNote> {
        val note = withRealm {
            val managedNote = where(RealmNote::class.java).equalTo("id", id).findFirst()

            if (managedNote == null) null else copyFromRealm(managedNote)
        }

        if (note == null) {
            return Observable.error(RuntimeException("No note exists with id $id"))
        }
        else {
            return Observable.just(note)
        }
    }

    override fun add(note: RealmNote): Observable<Int> {
        withRealmTransaction {
            note.giveId(this)
            insert(note)
        }
        return Observable.just(note.id)
    }

    override fun addAll(notes: List<RealmNote>): Observable<Int> {
        // TODO check for failure somehow
        withRealmTransaction {
            notes.giveIds(this)
            insert(notes)
        }
        return Observable.just(notes.size)
    }

    override fun update(note: RealmNote): Observable<Int> {
        // TODO check for failure somehow
        withRealmTransaction {
            insertOrUpdate(note)
        }
        return Observable.just(1)
    }

    override fun updateAll(notes: List<RealmNote>): Observable<Int> {
        // TODO check for failure somehow
        withRealmTransaction {
            insertOrUpdate(notes)
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

    private inline fun <T> withRealm(operation: Realm.() -> T): T {
        val realm = Realm.getDefaultInstance()
        realm.use { return it.operation() }
    }

    private inline fun <T> withRealmTransaction(operation: Realm.() -> T): T {
        var result: T? = null
        var exception: Exception? = null

        val realm = Realm.getDefaultInstance()

        realm.beginTransaction()
        try {
            result = realm.operation()
            realm.commitTransaction()
        } catch(e: Exception) {
            exception = e
            realm.cancelTransaction()
        } finally {
            realm.close()
            exception?.let { throw it }
        }

        return result as T
    }

}

