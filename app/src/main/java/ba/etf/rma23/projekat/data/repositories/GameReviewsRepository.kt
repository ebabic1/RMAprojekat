package ba.etf.rma23.projekat.data.repositories

import android.content.Context
import com.google.gson.JsonObject
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.withContext
class GameReviewsRepository{
object GameReviewsRepository {
    suspend fun getOfflineReviews(context : Context) : List<GameReview> {
        return  withContext(Dispatchers.IO){
            var db = AppDatabase.getInstance(context)
            var reviews = db!!.gameReviewDao().getAllOffline()
            return@withContext reviews
        }
    }
    suspend  fun  sendReview(context: Context, review: GameReview) : Boolean {
        return withContext(Dispatchers.IO) {
                    var list = AccountApiConfig.AccountGamesRepository.getSavedGames();
                    if (!list.any { it.id == review.igdb_id }) IGDBApiConfig.GamesRepository.getGameById(review.igdb_id)
                        ?.let { AccountApiConfig.AccountGamesRepository.saveGame(it) }
                        val paramObject = JsonObject()
                        paramObject.addProperty("review", review.review)
                        paramObject.addProperty("rating", review.rating)
                        var result = Api.ApiAdapter.retrofitAccount.saveReviewPOST(paramObject,review.igdb_id)
                        if(result.code() != 200){
                            var db = AppDatabase.getInstance(context)
                            db!!.gameReviewDao().insertAll(review)
                            return@withContext false}

                        return@withContext true
        }
    }

    suspend fun sendOfflineReviews(context: Context):Int{
        return withContext(Dispatchers.IO){

                var db = AppDatabase.getInstance(context)
                val list = getOfflineReviews(context)
                var i = 0
                for(record in list){
                        if(sendReview(context,record)) {
                            i++
                            db.gameReviewDao().setOnlineTrue(record.id)
                        }
                }
                return@withContext i;


        }
    }
    suspend fun getReviewsForGame(igdb_id:Int, context: Context):List<GameReview> {
        return withContext(Dispatchers.IO){

                var db = AppDatabase.getInstance(context)
                var list = Api.ApiAdapter.retrofitAccount.getReviewsForGame(igdb_id).body();
                var reviewList : MutableList<GameReview> = mutableListOf()
                if (list != null) {
                    for(response in list){
                        reviewList.add(GameReview(response.rating,response.review,response.GameId,false,response.id,response.timestamp,response.student))
                    }
                }

                return@withContext reviewList

        }
    }
}}