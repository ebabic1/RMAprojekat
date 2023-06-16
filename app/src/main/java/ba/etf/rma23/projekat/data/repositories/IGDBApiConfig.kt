package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.*
import kotlinx.coroutines.*
import okhttp3.MediaType
import okhttp3.MediaType.Companion.toMediaTypeOrNull
import okhttp3.RequestBody
import okhttp3.RequestBody.Companion.toRequestBody


class IGDBApiConfig {
    private val baseURL = "https://api.igdb.com/v4"
    object GamesRepository{
        suspend fun getGamesByName(name:String): List<Game> {
            return withContext(Dispatchers.IO){
                var response = Api.ApiAdapter.retrofit.getGamesByName(name)
                val responseBody = response.body()
                val returnList = responseBody?.let { Api.ApiAdapter.responseToGame(it) }
                if (returnList != null) {
                    if (!returnList.isEmpty()) {
                        GameData.initialGames = returnList as ArrayList<Game>
                        return@withContext returnList //mozda bude problem ako je null
                    }
                }
               return@withContext listOf()
            }
        }
        suspend fun getGameById(id : Int) : Game?{
            return withContext(Dispatchers.IO){
                var strBody = "fields cover.url,name,platforms.name,release_dates.date,total_rating,artworks.image_id,age_ratings.rating,age_ratings.category,involved_companies.company.name,involved_companies.publisher,genres.name,summary; where id = $id;"
                var body : RequestBody = strBody.toRequestBody("text/plain".toMediaTypeOrNull());
                var response = Api.ApiAdapter.retrofit.getGameById(body)
                val responseBody = response.body()
                val returnList = responseBody?.let { Api.ApiAdapter.responseToGame(it) }
                if (returnList != null) {
                    if (!returnList.isEmpty()) {
                        GameData.initialGames = returnList as ArrayList<Game>
                        return@withContext returnList[0] //mozda bude problem ako je null
                    }
                }
                return@withContext null;
            }
        }
        suspend fun getGamesSafe(name:String):List<Game>?{
            return withContext(Dispatchers.IO){
                var response : ArrayList<Game> = arrayListOf()
                if (AccountApiConfig.AccountGamesRepository.getAge() == null)
                    return@withContext response

                response = getGamesByName(name) as ArrayList<Game>

                return@withContext Api.ApiAdapter.removeNonSafeFromList(response)
            }
        }
        suspend fun getAuth(): AuthPostResponse?{
            return withContext(Dispatchers.IO){
                var response = Api.ApiAdapter.twitch.getAuth()
                val responseBody = response.body()
                return@withContext responseBody
            }
        }


        suspend fun sortGames():List<Game>{
            val sortingList = AccountApiConfig.AccountGamesRepository.getSavedGames()
            val sortedList = GameData.initialGames.sortedByDescending {sortingList.contains(it)}
            return sortedList
        }

    }

}