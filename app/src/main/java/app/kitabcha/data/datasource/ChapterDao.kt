package app.kitabcha.data.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.kitabcha.data.entity.ChapterEntity

@Dao
interface ChapterDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg chp: ChapterEntity)

    @Delete
    suspend fun delete(chp: ChapterEntity)

    @Query("SELECT * FROM ChaptersEntity WHERE owner_manga_id = :mngaID ORDER BY chapter_num")
    fun getMangaChapters(mngaID: Int): List<ChapterEntity>
}
