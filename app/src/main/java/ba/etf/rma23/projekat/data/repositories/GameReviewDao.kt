package ba.etf.rma23.projekat.data.repositories

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.Query

@Dao
interface GameReviewDao {
    @Query("SELECT * FROM gamereview")
    suspend fun getAll() : List<GameReview>
    @Query("SELECT * FROM gamereview WHERE igdb_id = :id")
    suspend fun getAllForGame(id:Int) : List<GameReview>
    @Query("SELECT * FROM gamereview WHERE online = false")
    suspend fun getAllOffline() : List<GameReview>
    @Insert
    suspend fun insertAll(vararg gamereviews : GameReview)
    @Query("UPDATE gamereview SET online = true WHERE id = :id")
    suspend fun setOnlineTrue(id: Int)
}