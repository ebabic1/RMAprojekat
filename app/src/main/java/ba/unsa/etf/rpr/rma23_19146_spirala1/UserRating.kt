package ba.unsa.etf.rpr.rma23_19146_spirala1

data class UserRating(
    override val username: String,
    override val timestamp: Long,
    val rating: Double
):UserImpression(username,timestamp)

