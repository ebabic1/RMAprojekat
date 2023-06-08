package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.*
import kotlinx.coroutines.*
import kotlin.collections.ArrayList

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