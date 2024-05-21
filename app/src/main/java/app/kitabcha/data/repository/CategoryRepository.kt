package app.kitabcha.data.repository

import app.kitabcha.data.datasource.CategoryDao
import app.kitabcha.data.entity.CategoryEntity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface CategoryRepository {
    suspend fun insert(cat: CategoryEntity)

    suspend fun delete(cat: CategoryEntity)

    suspend fun delete(cID: Int)

    suspend fun getCategories(lID: Int): Flow<List<CategoryEntity>>

    suspend fun getCategoryFromID(cID: Int): CategoryEntity
}

class CategoryRepositoryImpl
    @Inject
    constructor(
        private val dao: CategoryDao,
    ) : CategoryRepository {
        override suspend fun insert(cat: CategoryEntity) {
            withContext(IO) {
                dao.insert(cat)
            }
        }

        override suspend fun delete(cat: CategoryEntity) {
            withContext(IO) {
                dao.delete(cat)
            }
        }

        override suspend fun delete(cID: Int) {
            withContext(IO) {
                dao.delete(cID)
            }
        }

        override suspend fun getCategories(lID: Int): Flow<List<CategoryEntity>> {
            return withContext(IO) {
                dao.getCategories(lID)
            }
        }

        override suspend fun getCategoryFromID(cID: Int): CategoryEntity {
            return withContext(IO) {
                dao.getCategoryFromID(cID)
            }
        }
    }
