package app.kitabcha.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = LIBRARIES_ENTITY,
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["owner_id"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [Index(value = ["owner_id"], unique = true)],
)
data class LibraryEntity(
    @PrimaryKey(autoGenerate = true)
    val libID: Int = 0,
    @ColumnInfo(name = "owner_id")
    val ownerUserID: Int,
)

const val LIBRARIES_ENTITY = "LibrariesEntity"
