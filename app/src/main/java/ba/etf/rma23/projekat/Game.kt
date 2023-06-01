package ba.etf.rma23.projekat

data class Game (
    val id : Int=0,
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
    val userImpressions: List<UserImpression>
)

