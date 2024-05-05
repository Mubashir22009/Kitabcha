package app.kitabcha.data.entity

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.Index
import androidx.room.PrimaryKey
import kotlinx.serialization.Serializable

@Serializable
@Entity(tableName= UsersEntity,indices = [Index(value = ["user_name", "password"], unique =true)])
data class UserEntity (
    @PrimaryKey(autoGenerate=true)
    val id: Int = 0,

    @ColumnInfo(name ="user_name")
    val userName: String,
    @ColumnInfo(name = "password")
    val password: String
)

const val UsersEntity = "UsersEntity"