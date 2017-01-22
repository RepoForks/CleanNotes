package co.zsmb.data

import android.support.test.InstrumentationRegistry
import android.support.test.runner.AndroidJUnit4
import co.zsmb.cleannotes.data.RealmNote
import io.realm.Realm
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class NotesDataSourceDiskTest {

    @Before
    fun setUp() {
        val context = InstrumentationRegistry.getTargetContext()
        Realm.init(context)

        val realm = Realm.getDefaultInstance()
        realm.beginTransaction()

        realm.deleteAll()

        val note = realm.createObject(RealmNote::class.java, 0)
        note.title = "test note"
        note.content = "hey there brown cow"

        realm.commitTransaction()
        realm.close()
    }

    @Test
    fun useRealm() {
        val realm = Realm.getDefaultInstance()

        val results = realm.where(RealmNote::class.java).findAll()

        assertEquals(results.size, 1)

        val note = results[0]

        assertEquals(0, note.id)
        assertEquals("test note", note.title)
        assertEquals("hi there brown cow", note.content)

        realm.close()
    }

}
