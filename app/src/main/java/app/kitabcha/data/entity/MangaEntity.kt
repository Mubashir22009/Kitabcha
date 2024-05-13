package app.kitabcha.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName= MangasEntity,indices = [Index(value = ["manga_url","source_id"], unique =true)])
data class MangaEntity (
    @PrimaryKey(autoGenerate=true)
    val mangaID: Int = 0,

    @ColumnInfo(name ="manga_url")
    val mangaURL: String,
    @ColumnInfo(name = "manga_title")
    val mangaTitle: String,
    @ColumnInfo(name = "manga_description")
    val mangaDesc: String,
    @ColumnInfo(name = "manga_tags")
    val mangaTag: String,
    @ColumnInfo(name = "author_name")
    val mangaAuthor: String,
    @ColumnInfo(name = "source_id")
    val sourceID: Long
)

const val MangasEntity = "MangasEntity"
