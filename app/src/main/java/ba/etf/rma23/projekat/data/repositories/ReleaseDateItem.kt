package ba.etf.rma23.projekat.data.repositories

import com.google.gson.annotations.SerializedName
import java.util.*

data class ReleaseDateItem (
        @SerializedName("date") val releaseDate : Long
        )