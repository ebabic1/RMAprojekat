package ba.etf.rma23.projekat.data.repositories

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName

@Entity
data class GameReview (
    @ColumnInfo(name = "rating") @SerializedName("rating") var rating : Int?,
    @ColumnInfo(name = "review") @SerializedName("review")var review : String?,
    @ColumnInfo(name = "igdb_id") @SerializedName("igdb_id")var igdb_id : Int,
    @ColumnInfo("online") @SerializedName("online") var online : Boolean = false,
    @PrimaryKey(autoGenerate = true) @SerializedName("id") var id: Int = 0,
    @ColumnInfo(name = "timestamp") @SerializedName("timestamp") var timestamp : Long = 0,
    @ColumnInfo(name = "student") @SerializedName("student") var student : String = "",
        )