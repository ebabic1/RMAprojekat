package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.*
import kotlinx.coroutines.*
import kotlin.collections.ArrayList

class IGDBApiConfig {
    private val baseURL = "https://api.igdb.com/v4"
    object GamesRepository{
        suspend fun getGamesByName(name:String):List<Game>?{
            return withContext(Dispatchers.IO){
                var response = Api.ApiAdapter.retrofit.getGamesByName(name)
                val responseBody = response.body()
                return@withContext responseBody?.let { Api.ApiAdapter.responseToGame(it) } //mozda bude problem ako je null
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

        /**
         * Ovo ne bi trebalo primati ništa?
         */
        suspend fun sortGames(f : MainActivity):List<Game>{
            val sortingList = AccountApiConfig.AccountGamesRepository.getSavedGames()
            val sortedList = GameData.initialGames.sortedByDescending {sortingList.contains(it)}
            val navHostFragment = f.supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container)
            if (navHostFragment != null){
                (navHostFragment.childFragmentManager.fragments.get(0) as HomeFragment).gameListAdapter.updateGames(sortedList)
            }
            return sortedList
        }

    }

}