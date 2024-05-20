package app.kitabcha.data.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.kitabcha.data.entity.CategoryEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cat: CategoryEntity)

    @Delete
    suspend fun delete(cat: CategoryEntity)

    @Query("SELECT * FROM CategoriesEntity WHERE library_id = :lID")
    fun getCategories(lID: Int): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM CategoriesEntity WHERE catID = :cID")
    fun getCategoryFromID(cID: Int): CategoryEntity
}
