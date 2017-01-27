package co.zsmb.cleannotes.data

import io.reactivex.Single

interface NoteDataSource {

    fun add(note: RealmNote): Single<Int>

    fun addAll(notes: List<RealmNote>): Single<List<Int>>

    fun delete(noteId: Int): Single<Boolean>

    fun deleteAll(noteIds: List<Int>): Single<Int>

    fun get(noteId: Int): Single<RealmNote>

    fun getAll(): Single<List<RealmNote>>

    fun update(note: RealmNote): Single<Boolean>

    fun updateAll(notes: List<RealmNote>): Single<Int>

}
