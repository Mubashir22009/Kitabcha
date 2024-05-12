package app.kitabcha.data.repository

import app.kitabcha.data.datasource.CategoryMangaDao
import app.kitabcha.data.entity.CategoryMangaEntity
import app.kitabcha.data.entity.MangaEntity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface CategoryMangaRepository {

    suspend fun insert(cM: CategoryMangaEntity)

    suspend fun delete(cM: CategoryMangaEntity)

    suspend fun getAllMangasIDInCurrCategory(categID: Int): Flow<List<MangaEntity>>

}

class CategoryMangaRepositoryImpl @Inject constructor(
    private val dao: CategoryMangaDao
) : CategoryMangaRepository {
    override suspend fun insert(cM: CategoryMangaEntity) {
        withContext(IO) {
            dao.insert(cM)
        }
    }

    override suspend fun delete(cM: CategoryMangaEntity) {
        withContext(IO) {
            dao.delete(cM)
        }
    }

    override suspend fun getAllMangasIDInCurrCategory(categID: Int): Flow<List<MangaEntity>> {
        return withContext(IO) {
            dao.getAllMangasIDInCurrCategory(categID)
        }
    }
}
