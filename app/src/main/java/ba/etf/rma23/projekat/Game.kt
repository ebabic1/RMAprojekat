package ba.etf.rma23.projekat

import android.os.Parcelable
import com.google.gson.annotations.SerializedName

data class Game (
    val title: String,
    val platform: String,
    val releaseDate: String,
    val rating: Double,
    val coverImage: String,
    var esrbRating: String,
    val developer: String,
    var publisher: String,
    val genre: String,
    var description: String,
    val userImpressions: List<UserImpression>,
    val igdb_id : Int=0
)

