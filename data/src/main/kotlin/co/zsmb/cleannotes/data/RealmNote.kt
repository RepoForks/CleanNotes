package co.zsmb.cleannotes.data

import io.realm.RealmObject
import io.realm.annotations.PrimaryKey

open class RealmNote(
        @PrimaryKey var id: Int = -1,
        var title: String = "",
        var content: String = "")
    : RealmObject()
