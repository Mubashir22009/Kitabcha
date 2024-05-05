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

    @Insert(onConflict= OnConflictStrategy.IGNORE)
    suspend fun insert(user: UserEntity)

    @Delete
    suspend fun delete(user: UserEntity)

    @Query("SELECT password FROM UsersEntity WHERE :userName= user_name")
    fun getPassword(userName: String): Flow<List<String>>

    @Query("SELECT user_name FROM UsersEntity")
    fun getAllUserNames(): Flow<List<String>>
}