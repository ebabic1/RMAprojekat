package ba.unsa.etf.rpr.rma23_19146_spirala1

data class UserReview(
    override val username: String,
    override val timestamp: Long,
    val review: String
):UserImpression()


