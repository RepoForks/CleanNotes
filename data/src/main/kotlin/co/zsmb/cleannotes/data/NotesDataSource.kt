package co.zsmb.cleannotes.data

import io.reactivex.Observable

interface NotesDataSource {

    fun get(id: Int): Observable<RealmNote>

    fun getAll(): Observable<List<RealmNote>>

    fun add(note: RealmNote): Observable<Int>

    fun addAll(notes: List<RealmNote>): Observable<Int>

    fun update(note: RealmNote): Observable<Int>

    fun updateAll(notes: List<RealmNote>): Observable<Int>

}
