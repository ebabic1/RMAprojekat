package ba.unsa.etf.rpr.rma23_19146_spirala1

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GameDetailFragment : Fragment(){
    private lateinit var game : Game
    private lateinit var title : TextView
    private lateinit var platform : TextView
    private lateinit var release : TextView
    private lateinit var esrb: TextView
    private lateinit var developer : TextView
    private lateinit var genre: TextView
    private lateinit var publisher : TextView
    private lateinit var cover : ImageView
    private lateinit var impressionList : RecyclerView
    private lateinit var impressionListAdapter: ImpressionListAdapter
    private lateinit var description : TextView
    private lateinit var gameTitle : String
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View{
        val view = inflater.inflate(R.layout.game_details_fragment,container,false)
        title = view.findViewById(R.id.item_title_textview)
        cover = view.findViewById(R.id.cover_imageview)
        platform = view.findViewById(R.id.platform_textview)
        release = view.findViewById(R.id.release_date_textview)
        esrb = view.findViewById(R.id.esrb_rating_textview)
        developer = view.findViewById(R.id.developer_textview)
        genre = view.findViewById(R.id.genre_textview)
        publisher = view.findViewById(R.id.publisher_textview)
        description = view.findViewById(R.id.description_textview)
        gameTitle = arguments?.getString("game_title").toString()
        if (arguments == null) gameTitle = (activity as HomeActivity).gameTitle.toString() //workaround ali jedino ovako radi ako load fragmenta nije iniciran pritiskom buttona
        populateDetails(gameTitle)
        impressionList = view.findViewById(R.id.impression_recyclerview)
        impressionList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        impressionListAdapter = ImpressionListAdapter(listOf())
        impressionList.adapter = impressionListAdapter
        GameData.getDetails(game.title)
            ?.let { impressionListAdapter.updateImpressions(it.userImpressions) }
        return view
    }
    companion object {
        fun newInstance(): GameDetailFragment = GameDetailFragment()
    }
    private fun populateDetails(gameTitle : String){
        game = gameTitle.let { GameData.getDetails(it) }!!
        title.text = game.title
        platform.text = game.platform
        release.text = game.releaseDate
        esrb.text = game.esrbRating
        developer.text = game.developer
        genre.text = game.genre
        publisher.text = game.publisher
        description.text = game.description
        var context : Context = cover.context
        var id : Int = context.resources.getIdentifier(game.coverImage,"drawable",context.packageName)
        cover.setImageResource(id)
    }


}