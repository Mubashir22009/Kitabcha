package app.kitabcha.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName= CategoriesEntity,foreignKeys = [
    ForeignKey(
        entity = LibraryEntity::class,
        parentColumns = ["libID"],
        childColumns = ["library_id"],
        onDelete = ForeignKey.CASCADE
    )])
data class CategoryEntity (
    @PrimaryKey(autoGenerate=true)
    val catID: Int = 0,

    @ColumnInfo(name ="library_id")
    val myLibrary: Int,
    @ColumnInfo(name = "cat_title")
    val catTitle: String,

)

const val CategoriesEntity = "CategoriesEntity"
