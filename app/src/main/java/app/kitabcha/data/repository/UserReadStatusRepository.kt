package app.kitabcha.data.repository

import app.kitabcha.data.datasource.UserReadStatusDao
import app.kitabcha.data.entity.UserReadStatusEntity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface UserReadStatusRepository {
    suspend fun insert(userReadChapter: UserReadStatusEntity)

    suspend fun getMangaReadChaptersOfUser(
        userID: Int,
        mangaID: Int,
    ): List<Int>
}

class UserReadStatusRepositoryImpl
    @Inject
    constructor(
        private val dao: UserReadStatusDao,
    ) : UserReadStatusRepository {
        override suspend fun insert(userReadChapter: UserReadStatusEntity) {
            withContext(IO) {
                dao.insert(userReadChapter)
            }
        }

        override suspend fun getMangaReadChaptersOfUser(
            userID: Int,
            mangaID: Int,
        ): List<Int> {
            return withContext(IO) {
                dao.getMangaReadChaptersOfUser(userID, mangaID)
            }
        }
    }
