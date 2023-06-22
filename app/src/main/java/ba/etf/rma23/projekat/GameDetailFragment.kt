package ba.etf.rma23.projekat

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater

import android.view.View
import android.view.ViewGroup
import android.widget.*
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.data.repositories.AccountApiConfig
import ba.etf.rma23.projekat.data.repositories.GameReview
import ba.etf.rma23.projekat.data.repositories.GameReviewsRepository
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import java.net.UnknownHostException

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
    lateinit var impressionListAdapter: ImpressionListAdapter
    private lateinit var description : TextView
    private lateinit var gameTitle : String
    private lateinit var favoriteButton: ToggleButton
    private lateinit var submitReviewButton : Button
    private lateinit var reviewEditText: TextView
    private var favorited :Boolean = false
    private lateinit var ratingBar: RatingBar
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
        ratingBar = view.findViewById(R.id.ratingBar)
        esrb = view.findViewById(R.id.esrb_rating_textview)
        developer = view.findViewById(R.id.developer_textview)
        favoriteButton=view.findViewById(R.id.favorite_button)
        genre = view.findViewById(R.id.genre_textview)
        publisher = view.findViewById(R.id.publisher_textview)
        description = view.findViewById(R.id.description_textview)
        submitReviewButton = view.findViewById(R.id.submitreview_button)
        reviewEditText = view.findViewById(R.id.review_edittext)
        gameTitle = arguments?.getString("game_title").toString()
        if (arguments == null) gameTitle = (activity as MainActivity).gameTitle.toString() //workaround ali jedino ovako radi ako load fragmenta nije iniciran pritiskom buttona
        favoriteButton.isChecked = false;
        populateDetails(gameTitle)
        impressionList = view.findViewById(R.id.impression_recyclerview)
        impressionList.layoutManager = LinearLayoutManager(context, LinearLayoutManager.VERTICAL,false)
        impressionListAdapter = ImpressionListAdapter(listOf())
        impressionList.adapter = impressionListAdapter
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        var check = GameData.favoriteGames.find { it.title == gameTitle }
        favoriteButton.isChecked = check == null
        scope.launch {
            favoriteButton.isChecked = AccountApiConfig.AccountGamesRepository.getSavedGames().contains(GameData.getDetails(gameTitle))
            favorited = favoriteButton.isChecked
        }
        submitReviewButton.setOnClickListener {
            try{
                val scope = CoroutineScope(Job() + Dispatchers.Main)
                scope.launch {
                    val text : String = reviewEditText.text.toString();
                    var review : GameReview? = null
                    if (ratingBar.rating > 0){
                        review = GameReview(ratingBar.rating.toInt(),null,game.id)
                    }
                    else if (text.length>0)
                        review = GameReview(null,text,game.id)
                    val result = context?.let { it1 ->
                        if (review != null) {
                            GameReviewsRepository.GameReviewsRepository.sendReview(it1,review)
                        }
                    }
                    //Log.d("VALLJAL",result.toString())
                    refreshReviews()
                }
            }catch (e:java.net.UnknownHostException){

            }

        }
        GameData.getDetails(game.title)
            ?.let { impressionListAdapter.updateImpressions(it.userImpressions) }

        favoriteButton.setOnClickListener {
            val scope = CoroutineScope(Job() + Dispatchers.Main)
            scope.launch {
                if(!favorited)
                {
                    AccountApiConfig.AccountGamesRepository.saveGame(gameTitle.let { GameData.getDetails(it) }!!)
                    favorited = true
                }
                else GameData.getDetails(gameTitle)?.id?.let { it1 ->
                    AccountApiConfig.AccountGamesRepository.removeGame(
                        it1
                    )
                    favorited = false
                }

            }
        }
        return view
    }
    companion object {
        fun newInstance(): GameDetailFragment = GameDetailFragment()
    }
    private fun refreshReviews(){

            val scope = CoroutineScope(Job() + Dispatchers.Main)
            scope.launch {
                try {
                    var reviewList : List<GameReview>? =
                        GameReviewsRepository.GameReviewsRepository.getReviewsForGame(game.id)

                    var impressionList : MutableList<UserImpression> = mutableListOf()
                    if (reviewList != null) {
                        for ( review in reviewList){
                            if(review.review != null && review.rating ==null || review.rating == 0 )
                                impressionList.add(UserReview(review.student,review.timestamp,
                                    review.review!!
                                ))
                            else if (review.review == null && review.rating != null || review.review == "" )
                                impressionList.add(UserRating(review.student,review.timestamp, review.rating!!.toDouble()))

                        }
                    }
                    var offlineReviews =
                        context?.let { GameReviewsRepository.GameReviewsRepository.getOfflineReviews(it) };
                    if (offlineReviews != null) {
                        for(review in offlineReviews){
                            if (!review.online && review.igdb_id == game.id){
                                if (review.rating == null && review.review!=null)
                                    impressionList.add(UserReview(review.student,review.timestamp,"(OFFLINE REVIEW) ${review.review}"))
                                else if(review.rating!=null && review.review == null)
                                    impressionList.add(UserRating(review.student,review.timestamp,0.0))
                            }
                        }
                    }
                    impressionListAdapter.updateImpressions(impressionList as List<UserImpression>)

                }catch (e : UnknownHostException){
                    Toast.makeText(context,"Error loading reviews",Toast.LENGTH_SHORT).show()

           }}

    }
    private fun populateDetails(gameTitle : String){
        var prevGame : Game? = GameData.getDetails(gameTitle)
        if (prevGame != null)
        {   GameData.prevGame = prevGame
            game = gameTitle.let { GameData.getDetails(it) }!!}
        else {
            game = GameData.prevGame?:GameData.staticGames.get(0)
        }
        refreshReviews()
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
        val url = "https:${game.coverImage}"
        GlideApp.with(context)
            .load(url)
            .fallback(id)
            .error(id)
            .into(cover);

    }


}