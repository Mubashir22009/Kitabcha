package app.kitabcha.data.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.kitabcha.data.entity.UserEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface UserDao {
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(user: UserEntity)

    @Delete
    suspend fun delete(user: UserEntity)

    @Query("DELETE FROM UsersEntity WHERE id = :usrID")
    suspend fun delete(usrID: Int)

    @Query("SELECT * FROM UsersEntity WHERE :userName= user_name AND :userPassword= password")
    fun getUser(
        userName: String,
        userPassword: String,
    ): UserEntity?

    @Query("SELECT user_name FROM UsersEntity")
    fun getAllUserNames(): Flow<List<String>>

    @Query("SELECT * FROM UsersEntity WHERE id = :usrID")
    fun getUserFromID(usrID: Int): UserEntity

    @Query("SELECT COUNT(*) FROM UsersEntity")
    suspend fun getTotalUserCount(): Int
}
