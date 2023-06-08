package ba.etf.rma23.projekat.data.repositories

import com.google.gson.annotations.SerializedName

data class GetGamesResponse(
    @SerializedName("name") val title: String,
    @SerializedName("platforms") val platforms: List<PlatformItem>?,
    @SerializedName("release_dates") val releaseDates: List<ReleaseDateItem>?,
    @SerializedName("genres") val genres : List<GenreItem>?,
    @SerializedName("age_ratings") val ratings: List<RatingItem>?,
    @SerializedName("cover") val cover : CoverItem?,
    @SerializedName("involved_companies") val companies : List<ICompanyItem>?,
    @SerializedName("total_rating") val rating:Double?,
    @SerializedName("summary") val description: String?,
    @SerializedName("id") val igdb_id : Int
)
