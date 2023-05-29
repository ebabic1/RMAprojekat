package ba.etf.rma23.projekat.data.repositories

import com.google.gson.annotations.SerializedName

data class GetSavedGamesResponse(
    @SerializedName("id") val id : Int,
    @SerializedName("igdb_id") val igdb_id : Int,
    @SerializedName("name") val name: String,
    @SerializedName("createdAt") val createdAt : String,
    @SerializedName("updatedAt") val updatedAt : String

)