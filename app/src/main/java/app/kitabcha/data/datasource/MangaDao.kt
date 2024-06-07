package app.kitabcha.data.datasource

import android.database.sqlite.SQLiteConstraintException
import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import androidx.room.Update
import androidx.room.Upsert
import app.kitabcha.data.entity.MangaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface MangaDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg manga: MangaEntity)

    @Update
    suspend fun update(manga: MangaEntity)

    @Upsert
    suspend fun upsert(manga: MangaEntity)

    suspend fun upsertV2(manga: MangaEntity) { // Special upsert to check
        try {
            insert(manga)
        } catch (e: SQLiteConstraintException) {
            update(manga)
        }
    }

    @Delete
    suspend fun delete(manga: MangaEntity)

    @Query("SELECT * FROM MangasEntity WHERE manga_title LIKE '%' || :search || '%'")
    fun searchMangas(search: String): Flow<List<MangaEntity?>>

    @Query("SELECT * FROM MangasEntity")
    fun getAllMangas(): Flow<List<MangaEntity?>>

    @Query("SELECT * FROM MangasEntity WHERE mangaID = :mID")
    fun getMangaFromMID(mID: Int): MangaEntity

    @Query("SELECT mangaID FROM MangasEntity WHERE manga_url = :mUrl AND source_id = :srcId")
    fun getDBMangaFromSource(
        mUrl: String,
        srcId: Long,
    ): Int
}
