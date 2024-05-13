package app.kitabcha.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName= ChaptersEntity,foreignKeys = [
    ForeignKey(
        entity = MangaEntity::class,
        parentColumns = ["mangaID"],
        childColumns = ["owner_manga_id"],
        onDelete = ForeignKey.CASCADE
    )])
data class ChapterEntity (
    @PrimaryKey(autoGenerate=true)
    val chapterID: Int = 0,

    @ColumnInfo(name ="owner_manga_id")
    val ownerMangaID: Int,
    @ColumnInfo( name= "chapter_num")
    val chapterNum: Float,

    @ColumnInfo(name ="chapter_url")
    val chapterURL: String
    )

const val ChaptersEntity = "ChaptersEntity"
