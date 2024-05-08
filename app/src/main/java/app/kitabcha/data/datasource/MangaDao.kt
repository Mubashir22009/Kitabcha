package app.kitabcha.data.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.kitabcha.data.entity.MangaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MangaDao {

    @Insert(onConflict= OnConflictStrategy.IGNORE)
    suspend fun insert(manga: MangaEntity)

    @Delete
    suspend fun delete(manga: MangaEntity)

    @Query("SELECT * FROM MangasEntity WHERE manga_title LIKE '%' || :search || '%'")
    fun getLikeMangas(search: String): Flow<List<MangaEntity>>

    @Query("SELECT * FROM MangasEntity")
    fun getAllMangas(): Flow<List<MangaEntity>>
}