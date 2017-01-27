package co.zsmb.cleannotes.data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmNote(
        @PrimaryKey var id: Int = -1,
        var title: String = "",
        var content: String = "",
        var timestamp: Long = 0)
    : RealmObject() {

    override fun equals(other: Any?): Boolean {
        if (this === other) return true
        if (other?.javaClass != javaClass) return false

        other as RealmNote

        if (id != other.id) return false
        if (title != other.title) return false
        if (content != other.content) return false
        if (timestamp != other.timestamp) return false

        return true
    }

    override fun hashCode(): Int {
        var result = id
        result = 31 * result + title.hashCode()
        result = 31 * result + content.hashCode()
        result = 31 * result + timestamp.hashCode()
        return result
    }

}
