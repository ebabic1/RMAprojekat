package ba.etf.rma23.projekat.data.repositories

import com.google.gson.annotations.SerializedName

data class ArtworkItem(
    @SerializedName("image_id") val imageId : String,
    @SerializedName("url") val url : String
)
