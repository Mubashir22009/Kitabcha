package app.kitabcha.data.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.kitabcha.data.entity.LibraryEntity
import app.kitabcha.data.entity.MangaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LibraryDao {

    @Insert(onConflict= OnConflictStrategy.IGNORE)
    suspend fun insert(lib: LibraryEntity)

    @Delete
    suspend fun delete(lib: LibraryEntity)

    @Query("SELECT owner_id FROM LibrariesEntity WHERE libID = :lID")
    fun getUserID(lID: Int): Int

    @Query("SELECT libID FROM LibrariesEntity WHERE libID = :usrID")
    fun getLibID(usrID: Int): Int

}