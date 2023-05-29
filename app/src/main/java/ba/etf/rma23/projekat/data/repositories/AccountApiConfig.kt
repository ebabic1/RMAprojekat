package ba.etf.rma23.projekat.data.repositories

import android.widget.Toast
import ba.etf.rma23.projekat.Game
import com.google.gson.JsonObject
import kotlinx.coroutines.*



class AccountApiConfig {

    object AccountGamesRepository{

        const val baseUrl = "https://rma23ws.onrender.com/"
        private var localAge : Int? = null
        private var hash : String? = null
        fun setAge(age:Int): Boolean{
            if (age >= 3 && age <= 100){
                localAge = age
                return true
            }
            return false
        }
        fun getAge():Int?{
            return localAge
        }
        fun setHash(acHash:String): Boolean{
            hash = acHash
            if (hash != null) return true
            return false
        }
        fun getHash(): String?  {
            return hash
        }
        suspend fun removeGame(id:Int):Boolean{
            val scope = CoroutineScope(Job() + Dispatchers.Main)
            return withContext(Dispatchers.IO){
                val response = Api.ApiAdapter.retrofitAccount.removeGame(id)
                return@withContext response.code() == 200
            }
        }
        suspend fun removeNonSafe() : Boolean{
            return withContext(Dispatchers.IO){
                if(getAge()!=null)
                {
                    var nonSafeList : ArrayList<Game> = getSavedGames() as ArrayList<Game>
                    val safeList = Api.ApiAdapter.removeNonSafeFromList(nonSafeList)
                    nonSafeList.minus(safeList) as ArrayList<Game>
                    for(game in nonSafeList){
                        AccountApiConfig.AccountGamesRepository.removeGame(game.igdb_id)
                    }
                    return@withContext true
                }
                return@withContext false


            }
        }
        suspend fun saveGame(game: Game): Game {
            return withContext(Dispatchers.IO){
                val paramObject = JsonObject()
                paramObject.addProperty("igdb_id", game.igdb_id)
                paramObject.addProperty("name", game.title)
                val o = JsonObject()
                o.add("game",paramObject)
                var response = Api.ApiAdapter.retrofitAccount.saveGamePOST(o)
                return@withContext game
            }
        }
        suspend fun getGamesContainingString(query:String):List<Game>{
            return withContext(Dispatchers.IO){
                var gameList : ArrayList<Game> = getSavedGames() as ArrayList<Game>
                gameList = gameList.filter { game: Game -> game.title.lowercase().contains(query.lowercase()) } as ArrayList<Game>
                return@withContext gameList
            }
        }
        suspend fun getSavedGames() : List<Game>{
            return withContext(Dispatchers.IO){
                var gameList : ArrayList<Game> = arrayListOf()
                var response = Api.ApiAdapter.retrofitAccount.getSavedGamesPOST().body()
                if (response != null) {
                    for(item in response){
                        var nameGames = IGDBApiConfig.GamesRepository.getGamesByName(item.name)
                        if (nameGames != null) {
                            for(namegame in nameGames){
                                if (namegame.igdb_id == item.igdb_id) {
                                    gameList.add(namegame)
                                    break
                                }
                            }
                        }

                    }
                }
                return@withContext gameList
            }
        }
    }

}

