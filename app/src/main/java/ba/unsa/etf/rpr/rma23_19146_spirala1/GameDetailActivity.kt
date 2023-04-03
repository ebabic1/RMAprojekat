package ba.unsa.etf.rpr.rma23_19146_spirala1

import android.content.Context
import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class GameDetailActivity : AppCompatActivity(){
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
    private lateinit var impressions : List<UserImpression>
    private lateinit var impressionListAdapter: ImpressionListAdapter
    private lateinit var homeButton : Button
    private lateinit var detailsButton: Button
    private lateinit var logo : ImageView
    private lateinit var description : TextView


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.game_details_activity)
        title = findViewById(R.id.game_title_textview)
        cover = findViewById(R.id.cover_imageview)
        platform = findViewById(R.id.platform_textview)
        release = findViewById(R.id.release_date_textview)
        esrb = findViewById(R.id.esrb_rating_textview)
        developer = findViewById(R.id.developer_textview)
        genre = findViewById(R.id.genre_textview)
        publisher = findViewById(R.id.publisher_textview)
        homeButton = findViewById(R.id.home_button)
        detailsButton = findViewById(R.id.details_button)
        logo = findViewById(R.id.logo_image)
        description = findViewById(R.id.description_textview)
        val extras = intent.extras
        if(extras != null){
            game = GameData.getDetails(extras.getString("game_title",""))
            populateDetails()
        } else{
            finish()
        }
        homeButton.setOnClickListener(){
            val intent = Intent(this,HomeActivity::class.java).apply {
                putExtra("game_title",game.title)
            }
            startActivity(intent)
        }
        impressionList = findViewById(R.id.impression_recyclerview)
        impressionList.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        impressionListAdapter = ImpressionListAdapter(listOf())
        impressionList.adapter = impressionListAdapter
        impressionListAdapter.updateImpressions(GameData.getDetails(game.title).userImpressions)

    }

    /*override fun onBackPressed() { // sa ovim bi pamtilo zadnju igru i sa pritisnutim back ali asistent Irfan je rekao da nije problem ako ne implementiramo
        super.onBackPressed()
        val intent = Intent(this,HomeActivity::class.java).apply {
            putExtra("game_title",game.title)
        }
        startActivity(intent)
    }*/
    override fun onResume() {
        super.onResume()
        homeButton.isEnabled = true
    }
    private fun populateDetails(){
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