package app.kitabcha.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.kitabcha.data.entity.CategoryMangaEntity
import app.kitabcha.data.entity.MangaEntity

@Dao
interface CategoryMangaDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(cM: CategoryMangaEntity)

    @Query("DELETE FROM CategoryMangasEntity WHERE owner_Category_id = :catID AND manga_id = :mangaID")
    suspend fun delete(
        catID: Int,
        mangaID: Int,
    )

    @Query("SELECT m.* FROM CategoryMangasEntity cm JOIN MangasEntity m ON cm.manga_id=m.mangaID  WHERE cm.owner_Category_id = :categID")
    fun getAllMangasIDInCurrCategory(categID: Int): List<MangaEntity>
}
