package app.kitabcha.ui.Repository

import androidx.lifecycle.LiveData
import app.kitabcha.data.User
import app.kitabcha.data.UserDao

class Repository(
    private val userdao: UserDao,
    val usernames: LiveData<List<String>> = userdao.getAllUserNames()
)
{
    suspend fun addUser(user: User)
    {
        userdao.addUser(user)
    }
}