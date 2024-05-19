package app.kitabcha.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.PrimaryKey

@Entity(
    tableName = CATEGORIES_ENTITY,
    foreignKeys = [
        ForeignKey(
            entity = LibraryEntity::class,
            parentColumns = ["libID"],
            childColumns = ["library_id"],
            onDelete = ForeignKey.CASCADE,
        ),
    ],
    indices = [androidx.room.Index(value = ["library_id", "cat_title"], unique = true)],
)
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true)
    val catID: Int = 0,
    @ColumnInfo(name = "library_id")
    val myLibrary: Int,
    @ColumnInfo(name = "cat_title")
    var catTitle: String,
)

const val CATEGORIES_ENTITY = "CategoriesEntity"
