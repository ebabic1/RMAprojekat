package ba.etf.rma23.projekat.data.repositories

import com.google.gson.annotations.SerializedName

data class AuthPostResponse(
    @SerializedName("access_token") val page: String,
    @SerializedName("expires_in") val games: Int,
    @SerializedName("token_type") val pages: String
)
