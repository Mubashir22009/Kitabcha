package app.kitabcha.data

import android.content.Context
import androidx.compose.ui.platform.LocalContext
import androidx.room.Database
import androidx.room.Room
import androidx.room.RoomDatabase


@Database(entities = [User::class], version= 1)
abstract class MainDatabase : RoomDatabase() {
    abstract fun userDao(): UserDao

    companion object{
        @Volatile
        private var INSTANCE: MainDatabase? = null

        fun getDataBase(context: Context): MainDatabase {
            val tempInstance = INSTANCE
            if(tempInstance !=null)
            {
                return tempInstance
            }
            synchronized(this){
                val instance = Room.databaseBuilder(
                    context.applicationContext,
                    MainDatabase::class.java,
                    "main_database"
                ).build()
                INSTANCE =instance
                return instance
            }
        }
    }
}