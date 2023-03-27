package ba.unsa.etf.rpr.rma23_19146_spirala1

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView

class GameListAdapter(
    private var games: List<Game>, private val onItemClicked : (game : Game) -> Unit
) : RecyclerView.Adapter<GameListAdapter.GameViewHolder>() {

    fun updateGames(games:List<Game>){
        this.games = games
        notifyDataSetChanged()
    }
    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): GameViewHolder {
        val view = LayoutInflater.from(parent.context).inflate(R.layout.item_game,parent,false)
        return GameViewHolder(view)
    }

    override fun getItemCount(): Int = games.size

    override fun onBindViewHolder(holder: GameViewHolder, position: Int) {
        holder.gamePlatform.text = games[position].platform
        holder.gameRelease.text = games[position].releaseDate
        holder.gameTitle.text = games[position].title
        holder.gameRating.text = games[position].rating.toString()
        holder.itemView.setOnClickListener{onItemClicked(games[position])}
    }
    inner class GameViewHolder(itemView : View) : RecyclerView.ViewHolder(itemView) {
        val gameTitle :TextView= itemView.findViewById(R.id.game_title_textview1)
        val gameRelease :TextView= itemView.findViewById(R.id.game_release_date_textview)
        val gamePlatform :TextView= itemView.findViewById(R.id.game_platform_textview)
        val gameRating :TextView= itemView.findViewById(R.id.game_rating_textview)
    }
}