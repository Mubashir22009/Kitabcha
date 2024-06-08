package app.kitabcha.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(
    tableName = USER_READ_STATUS_ENTITY,
    indices = [Index(value = ["user_read_id", "manga_read_id", "chapter_read_id"], unique = true)],
    foreignKeys = [
        ForeignKey(
            entity = UserEntity::class,
            parentColumns = ["id"],
            childColumns = ["user_read_id"],
            onDelete = ForeignKey.CASCADE,
        ), ForeignKey(
            entity = MangaEntity::class,
            parentColumns = ["mangaID"],
            childColumns = ["manga_read_id"],
            onDelete = ForeignKey.CASCADE,
        ), ForeignKey(
            entity = ChapterEntity::class,
            parentColumns = ["chapterID"],
            childColumns = ["chapter_read_id"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
)
data class UserReadStatusEntity(
    @PrimaryKey(autoGenerate = true)
    val readID: Int = 0,
    @ColumnInfo(name = "user_read_id")
    val userReadID: String,
    @ColumnInfo(name = "manga_read_id")
    val mangaReadID: String,
    @ColumnInfo(name = "chapter_read_id")
    val chapterReadID: Int,
)

const val USER_READ_STATUS_ENTITY = "UserReadStatusEntity"
