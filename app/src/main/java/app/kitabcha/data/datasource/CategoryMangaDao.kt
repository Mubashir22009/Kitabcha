package app.kitabcha.data.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.kitabcha.data.entity.CategoryMangaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoryMangaDao {

    @Insert(onConflict= OnConflictStrategy.IGNORE)
    suspend fun insert(cM: CategoryMangaEntity)

    @Delete
    suspend fun delete(cM: CategoryMangaEntity)

    @Query("SELECT manga_id FROM CategoryMangasEntity WHERE owner_Category_id = :categID")
    fun getAllMangasIDInCurrCategory(categID: Int): Flow<List<Int>>

}
