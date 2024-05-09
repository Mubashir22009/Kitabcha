package app.kitabcha.data.repository

import app.kitabcha.data.datasource.LibraryDao
import app.kitabcha.data.entity.LibraryEntity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface LibraryRepository {

    suspend fun insert(lib: LibraryEntity)

    suspend fun delete(lib: LibraryEntity)

    suspend fun getUserID(lID: Int): Flow<List<Int>>

    suspend fun getLibID(usrID: Int): Flow<List<Int>>
}

class LibraryRepositoryImpl @Inject constructor(
    private val dao: LibraryDao
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

    override suspend fun getUserID(lID: Int): Flow<List<Int>> {
        return withContext(IO) {
            dao.getUserID(lID)
        }
    }

    override suspend fun getLibID(usrID: Int): Flow<List<Int>>{
        return withContext(IO) {
            dao.getLibID(usrID)
        }
    }
}