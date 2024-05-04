package app.kitabcha.data

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey

@Entity(tableName="USERS",indices = [Index(value = ["user_name", "password"], unique =true)])
data class User (
    @PrimaryKey(autoGenerate=true)
    val id: Int,
    @ColumnInfo(name ="user_name") val userName: String,
    @ColumnInfo(name = "password") val password: String
)