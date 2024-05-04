package app.kitabcha.data

import androidx.lifecycle.LiveData
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface UserDao {

    @Insert(onConflict= OnConflictStrategy.IGNORE)
    suspend fun addUser(vararg newUser: User)

    @Query("SELECT password FROM USERS WHERE :userName= user_name")
    suspend fun getPassword(userName: String): List<String>

    @Query("SELECT user_name FROM USERS")
    fun getAllUserNames():LiveData<List<String>>
}