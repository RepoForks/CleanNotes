package co.zsmb.cleannotes.data

import co.zsmb.cleannotes.domain.DomainNote

object DomainNoteMapper {

    private val realmToDomain: (RealmNote) -> DomainNote = { DomainNote(it.id, it.title, it.content, it.timestamp) }
    private val domainToRealm: (DomainNote) -> RealmNote = { RealmNote(it.id, it.title, it.content, it.timestamp) }

    fun DomainNote.toRealmNote(): RealmNote = domainToRealm(this)

    fun RealmNote.toDomainNote(): DomainNote = realmToDomain(this)

    fun List<DomainNote>.toRealmNotes(): List<RealmNote> = this.map(domainToRealm)

    fun List<RealmNote>.toDomainNotes(): List<DomainNote> = this.map(realmToDomain)

}
