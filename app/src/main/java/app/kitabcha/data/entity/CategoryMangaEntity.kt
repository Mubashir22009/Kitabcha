package app.kitabcha.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName= CategoryMangasEntity,foreignKeys = [
    ForeignKey(
        entity = CategoryEntity::class,
        parentColumns = ["catID"],
        childColumns = ["owner_Category_id"],
        onDelete = ForeignKey.CASCADE
    ),ForeignKey(
        entity = MangaEntity::class,
        parentColumns = ["mangaID"],
        childColumns = ["manga_id"],
        onDelete = ForeignKey.CASCADE
    )])
data class CategoryMangaEntity (
    @PrimaryKey(autoGenerate=true)
    val cmID: Int = 0,

    @ColumnInfo(name ="owner_Category_id")
    val ownerCatID: Int,

    @ColumnInfo(name = "manga_id")
    val mangID: Int
)

const val CategoryMangasEntity = "CategoryMangasEntity"
