package ba.etf.rma23.projekat.data.repositories

import com.google.gson.annotations.SerializedName

data class GameReviewResponse(
    @SerializedName("id") val id : Int,
    @SerializedName("rating") val rating : Int,
    @SerializedName("timestamp") val timestamp : Long,
    @SerializedName("review") val review : String,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updatedAt") val updatedAt : String,
    @SerializedName("student") val student : String,
    @SerializedName("GameId") val GameId : Int,
)
