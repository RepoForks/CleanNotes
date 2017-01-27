package co.zsmb.cleannotes.data

import co.zsmb.cleannotes.data.DomainNoteMapper.toDomainNote
import co.zsmb.cleannotes.data.DomainNoteMapper.toDomainNotes
import co.zsmb.cleannotes.data.DomainNoteMapper.toRealmNote
import co.zsmb.cleannotes.data.DomainNoteMapper.toRealmNotes
import co.zsmb.cleannotes.domain.DomainNote
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.Parameterized

@RunWith(Parameterized::class)
class DomainNoteMapperTest(params: Pair<List<DomainNote>, List<RealmNote>>) {

    val domainNotes = params.first
    val realmNotes = params.second

    @Test
    fun toRealmNote() {
        domainNotes.forEachIndexed { i, domainNote ->
            assertEquals(realmNotes[i], domainNote.toRealmNote())
        }
    }

    @Test
    fun toDomainNote() {
        realmNotes.forEachIndexed { i, realmNote ->
            assertEquals(domainNotes[i], realmNote.toDomainNote())
        }
    }

    @Test
    fun toRealmNotes() {
        assertEquals(domainNotes.toRealmNotes(), realmNotes)
    }

    @Test
    fun toDomainNotes() {
        assertEquals(realmNotes.toDomainNotes(), domainNotes)
    }

    companion object {

        @JvmStatic @Parameterized.Parameters
        fun data(): List<Pair<List<DomainNote>, List<RealmNote>>> {
            val listOfPairs = mutableListOf<Pair<List<DomainNote>, List<RealmNote>>>()

            listOf(1, 2, 5, 10).forEach {
                val domainNotes = mutableListOf<DomainNote>()
                val realmNotes = mutableListOf<RealmNote>()

                for (i in 1..it) {
                    val id = i
                    val title = "title$i"
                    val content = "content$i"
                    val timestamp = (i * i * i * i).toLong()

                    domainNotes.add(DomainNote(id, title, content, timestamp))
                    realmNotes.add(RealmNote(id, title, content, timestamp))
                }

                listOfPairs.add(domainNotes to realmNotes)
            }

            return listOfPairs
        }

    }

}