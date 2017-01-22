package co.zsmb.cleannotes.domain

import io.reactivex.Observable

interface NotesRepository {

    fun get(id: Int): Observable<DomainNote>

    fun getAll(): Observable<List<DomainNote>>

    fun add(note: DomainNote): Observable<Int>

    fun addAll(notes: List<DomainNote>): Observable<Int>

    fun update(note: DomainNote): Observable<Int>

    fun updateAll(notes: List<DomainNote>): Observable<Int>

}
