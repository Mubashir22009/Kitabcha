package app.kitabcha.data.repository

import app.kitabcha.data.datasource.MangaDao
import app.kitabcha.data.entity.MangaEntity
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.withContext
import javax.inject.Inject

interface MangaRepository {

    suspend fun insert(manga: MangaEntity)

    suspend fun delete(manga: MangaEntity)

    suspend fun searchMangas(search: String): Flow<List<MangaEntity?>>

    suspend fun getAllMangas(): Flow<List<MangaEntity?>>
}

class MangaRepositoryImpl @Inject constructor(
    private val dao: MangaDao
) : MangaRepository {
    override suspend fun insert(manga: MangaEntity) {
        withContext(IO) {
            dao.insert(manga)
        }
    }

    override suspend fun delete(manga: MangaEntity) {
        withContext(IO) {
            dao.delete(manga)
        }
    }

    override suspend fun searchMangas(search: String): Flow<List<MangaEntity?>> {
        return withContext(IO) {
            dao.searchMangas(search)
        }
    }

    override suspend fun getAllMangas(): Flow<List<MangaEntity?>>{
        return withContext(IO) {
            dao.getAllMangas()
        }
    }
}
