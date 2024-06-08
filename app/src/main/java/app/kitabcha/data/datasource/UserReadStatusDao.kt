package app.kitabcha.data.datasource

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.kitabcha.data.entity.UserReadStatusEntity

@Dao
interface UserReadStatusDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(vararg userRead: UserReadStatusEntity)

    @Query(
        "SELECT chapter_read_id FROM UserReadStatusEntity WHERE user_read_id = :userID AND manga_read_id = :mangaID " +
            "ORDER BY chapter_read_id",
    )
    fun getMangaReadChaptersOfUser(
        userID: Int,
        mangaID: Int,
    ): List<Int>?
}
