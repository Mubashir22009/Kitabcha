package app.kitabcha.data.repository

import app.kitabcha.data.datasource.LibraryDao
import app.kitabcha.data.entity.CategoryEntity
import app.kitabcha.data.entity.LibraryEntity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface LibraryRepository {
    suspend fun insert(lib: LibraryEntity)

    suspend fun delete(lib: LibraryEntity)

    suspend fun getLibID(lib: Int): Int

    suspend fun getAllCategoriesOfUser(usrID: Int): List<CategoryEntity>
}

class LibraryRepositoryImpl
    @Inject
    constructor(
        private val dao: LibraryDao,
    ) : LibraryRepository {
        override suspend fun insert(lib: LibraryEntity) {
            withContext(IO) {
                dao.insert(lib)
            }
        }

        override suspend fun delete(lib: LibraryEntity) {
            withContext(IO) {
                dao.delete(lib)
            }
        }

        override suspend fun getLibID(usrID: Int): Int {
            return withContext(IO) {
                dao.getLibID(usrID)
            }
        }

        override suspend fun getAllCategoriesOfUser(usrID: Int): List<CategoryEntity> {
            return withContext(IO) {
                dao.getAllCategoriesOfUser(usrID)
            }
        }
    }
