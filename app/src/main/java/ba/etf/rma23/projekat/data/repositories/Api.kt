package ba.etf.rma23.projekat.data.repositories

import ba.etf.rma23.projekat.BuildConfig
import ba.etf.rma23.projekat.Game
import com.google.gson.JsonObject
import okhttp3.OkHttpClient

import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Response
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*
import java.time.Instant
import java.time.format.DateTimeFormatter

interface Api {
    @Headers("Client-ID: ${BuildConfig.IGDB_CLIENT_ID}", "Authorization: ${BuildConfig.IGDB_API_KEY}")
    @GET("games")
    suspend fun getGamesByName(
        @Query("search") search : String, @Query("fields") fields: String = "cover.url,name,platforms.name,release_dates.date,total_rating,artworks.image_id,age_ratings.rating,age_ratings.category,involved_companies.company.name,involved_companies.publisher,genres.name,summary"
    ): Response<List<GetGamesResponse>>
    @POST("token")
    suspend fun getAuth(@Field("client_id") client_id : String= "w8r77nwoos0xww2uamczbyqvqn0j2o",
                        @Field("client_secret") client_secret:String ="qtlqhxadjd9y4jjit6j8ksm5zieghw",
                        @Field("grant_type") grant_type :String = "client_credentials") : Response<AuthPostResponse>
    @Headers("Content-Type: application/json; charset=utf-8")
    @POST("account/{hash}/game")
    suspend fun saveGamePOST( @Body j : JsonObject,@Path("hash") hash: String? = AccountApiConfig.AccountGamesRepository.getHash())
    @GET("account/{hash}/games")
    suspend fun getSavedGamesPOST(@Path("hash") hash: String? = AccountApiConfig.AccountGamesRepository.getHash()) : Response<List<GetSavedGamesResponse>>
    @DELETE("account/{hash}/game/{gid}")
    suspend fun removeGame(@Path("gid") gid: Int,@Path("hash") hash: String? = AccountApiConfig.AccountGamesRepository.getHash()) : Response<Void>
    object ApiAdapter {
        val okhttp = OkHttpClient.Builder().addInterceptor(HttpLoggingInterceptor().apply {
            level = HttpLoggingInterceptor.Level.BODY
        }).build()
        val retrofit : Api = Retrofit.Builder()
            .baseUrl("https://api.igdb.com/v4/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttp)
            .build()
            .create(Api::class.java)
        val twitch : Api = Retrofit.Builder()
            .baseUrl("https://id.twitch.tv/oauth2/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttp)
            .build()
            .create(Api::class.java)
        val retrofitAccount : Api = Retrofit.Builder()
            .baseUrl("https://rma23ws.onrender.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .client(okhttp)
            .build()
            .create(Api::class.java)
        fun removeNonSafeFromList( temp:ArrayList<Game>) : ArrayList<Game>{
            var i = 0;
            while(i<temp.size){
                if(temp[i].esrbRating == "E10" && AccountApiConfig.AccountGamesRepository.getAge()!! < 10 ||
                temp[i].esrbRating == "T" && AccountApiConfig.AccountGamesRepository.getAge()!! < 13 ||
                temp[i].esrbRating == "M" && AccountApiConfig.AccountGamesRepository.getAge()!! < 17 ||
                temp[i].esrbRating == "AO" && AccountApiConfig.AccountGamesRepository.getAge()!! < 18  || temp[i].esrbRating == "") {
                    temp.removeAt(i)
                    i--
                }
                i++
            }
            return temp
        }
        fun responseToGame( responseBody : List<GetGamesResponse>) : List<Game>{
            var newGames: ArrayList<Game> = arrayListOf()
            for(game in responseBody){
                val item = game.companies?.get(0)?.company?.let {
                    Game(game.igdb_id,game.title,
                        game.platforms?.get(0)?.name ?: "",
                        DateTimeFormatter.ISO_INSTANT
                            .format(Instant.ofEpochSecond(game.releaseDates?.get(0)?.releaseDate ?:0)),Math.round(game.rating?.times(
                            100.0
                        ) ?: 0.0) / 100.0?:0.0,
                        game.cover?.url ?:"","", it.name,"",
                        game.genres?.get(0)?.name ?:"",game.description.toString()?:"",
                        listOf()
                    )

                }

                if(game.companies != null && game.companies.size >0)
                    for(company in game.companies){
                        if (company.publisher){
                            if (item != null) {
                                item.publisher = company.company.name
                            }
                        }
                    }
                var esrbRating : String = ""
                if(game.ratings != null)
                    for(rating in game.ratings){
                        if (rating.category == 1) {
                            when (rating.rating){
                                8 -> esrbRating = "E"
                                9 -> esrbRating = "E10"
                                10 -> esrbRating = "T"
                                11 -> esrbRating = "M"
                                12 -> esrbRating = "AO"
                            }
                            break
                        }
                    }
                if (item != null) {
                    item.esrbRating = esrbRating
                }
                if (item != null) {
                    newGames.add(item)
                }
            }
            return newGames
        }
    }
}