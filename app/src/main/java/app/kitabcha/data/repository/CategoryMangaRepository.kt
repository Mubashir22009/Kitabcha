package app.kitabcha.data.repository

import app.kitabcha.data.datasource.CategoryMangaDao
import app.kitabcha.data.entity.CategoryMangaEntity
import app.kitabcha.data.entity.MangaEntity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface CategoryMangaRepository {
    suspend fun insert(cM: CategoryMangaEntity)

    suspend fun delete(usrID: Int)

    suspend fun getAllMangasIDInCurrCategory(categID: Int): List<MangaEntity>
}

class CategoryMangaRepositoryImpl
    @Inject
    constructor(
        private val dao: CategoryMangaDao,
    ) : CategoryMangaRepository {
        override suspend fun insert(cM: CategoryMangaEntity) {
            withContext(IO) {
                dao.insert(cM)
            }
        }

        override suspend fun delete(usrID: Int) {
            withContext(IO) {
                dao.delete(usrID)
            }
        }

        override suspend fun getAllMangasIDInCurrCategory(categID: Int): List<MangaEntity> {
            return withContext(IO) {
                dao.getAllMangasIDInCurrCategory(categID)
            }
        }
    }
