package app.kitabcha.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(tableName= LibrariesEntity,foreignKeys = [
    ForeignKey(
        entity = UserEntity::class,
        parentColumns = ["id"],
        childColumns = ["libID"],
        onDelete = ForeignKey.CASCADE
    )])
data class LibraryEntity (
    @PrimaryKey(autoGenerate=true)
    val libID: Int = 0,

    @ColumnInfo(name ="owner_id")
    val ownerUserID: Int
)

const val LibrariesEntity = "LibrariesEntity"

