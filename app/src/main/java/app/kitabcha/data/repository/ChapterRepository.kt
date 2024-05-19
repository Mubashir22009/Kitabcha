package app.kitabcha.data.repository

import app.kitabcha.data.datasource.ChapterDao
import app.kitabcha.data.entity.ChapterEntity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface ChapterRepository {
    suspend fun insert(vararg chp: ChapterEntity)

    suspend fun delete(chp: ChapterEntity)

    suspend fun getMangaChapters(mngaID: Int): List<ChapterEntity>
}

class ChapterRepositoryImpl
    @Inject
    constructor(
        private val dao: ChapterDao,
    ) : ChapterRepository {
        override suspend fun insert(vararg chp: ChapterEntity) {
            withContext(IO) {
                dao.insert(*chp)
            }
        }

        override suspend fun delete(chp: ChapterEntity) {
            withContext(IO) {
                dao.delete(chp)
            }
        }

        override suspend fun getMangaChapters(mngaID: Int): List<ChapterEntity> {
            return withContext(IO) {
                dao.getMangaChapters(mngaID)
            }
        }
    }
