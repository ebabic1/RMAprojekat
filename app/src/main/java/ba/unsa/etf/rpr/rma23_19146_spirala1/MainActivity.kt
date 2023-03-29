package ba.unsa.etf.rpr.rma23_19146_spirala1

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.MenuItem
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import android.widget.LinearLayout
import android.widget.Toast
import androidx.appcompat.app.ActionBar
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView

class MainActivity : AppCompatActivity() {
    private lateinit var homeButton : ImageButton
    private lateinit var gameList : RecyclerView
    private lateinit var gameListAdapter: GameListAdapter
    private var games = GameData.getAll()
    private lateinit var detailsButton: ImageButton
    private var gameTitle : String? = null
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        homeButton = findViewById(R.id.home_button)
        detailsButton = findViewById(R.id.details_button)
        gameList = findViewById(R.id.game_list)
        gameList.layoutManager = LinearLayoutManager(this,LinearLayoutManager.VERTICAL,false)
        gameListAdapter = GameListAdapter(arrayListOf()){game -> showGameDetails(game)}
        gameList.adapter = gameListAdapter
        gameListAdapter.updateGames(games)
        detailsButton.isEnabled = false
        detailsButton.setOnClickListener(){
            gameTitle?.let { it1 -> GameData.getDetails(it1) }
                ?.let { it2 -> showGameDetails(it2) }
        }
    }

    override fun onResume() {
        super.onResume()
        homeButton.isEnabled = false
        val extras = intent.extras
        if(extras != null){
            gameTitle = extras.getString("game_title","")
            detailsButton.isEnabled = gameTitle != ""              //Ako nismo upravo gledali detalje igre gameTitle je prazan string
        }

    }
    private fun showGameDetails(game:Game){
        val intent = Intent(this,GameDetailActivity::class.java).apply {
            putExtra("game_title",game.title)
        }
        startActivity(intent)
    }
}