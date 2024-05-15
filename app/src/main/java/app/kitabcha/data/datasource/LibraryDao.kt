package app.kitabcha.data.datasource

import androidx.room.Dao
import androidx.room.Delete
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import app.kitabcha.data.entity.CategoryEntity
import app.kitabcha.data.entity.LibraryEntity
import app.kitabcha.data.entity.MangaEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface LibraryDao {

    @Insert(onConflict= OnConflictStrategy.IGNORE)
    suspend fun insert(lib: LibraryEntity)

    @Delete
    suspend fun delete(lib: LibraryEntity)

    @Query("SELECT libID FROM LibrariesEntity WHERE  owner_id= :usrID")
    fun getLibID(usrID: Int): Int

    @Query("SELECT c.* FROM LibrariesEntity l JOIN CategoriesEntity c ON l.libID=c.library_id WHERE l.owner_id = :usrID")
    fun getAllCategoriesOfUser(usrID: Int): List<CategoryEntity>

}