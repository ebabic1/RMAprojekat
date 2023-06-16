package ba.etf.rma23.projekat

import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Button
import androidx.core.os.bundleOf
import androidx.navigation.NavOptions
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.data.repositories.GameReview
import ba.etf.rma23.projekat.data.repositories.GameReviewsRepository
import com.google.android.material.bottomnavigation.BottomNavigationView

class MainActivity : AppCompatActivity() {
    private lateinit var homeButton: Button
    private lateinit var gameList: RecyclerView
    lateinit var gameListAdapter: GameListAdapter
    private var games = GameData.getAll()
    private lateinit var detailsButton: Button

    var gameTitle: String? = if (GameData.getAll().size>0) GameData.getAll()[0].title else ""
    var navView: BottomNavigationView? = null

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        navController.navigate(R.id.homeItem,null,NavOptions.Builder().setPopUpTo(R.id.homeItem,true).build())
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT)
        {
            navView = findViewById(R.id.bottom_nav)
            navView?.setupWithNavController(navController)
            navView?.setOnItemSelectedListener { it ->
                if (it.itemId == R.id.gameDetailsItem) {
                    val bundle = bundleOf("game_title" to gameTitle)
                    navController.navigate(R.id.gameDetailsItem, bundle)
                }
                if (it.itemId == R.id.homeItem) {
                    navController.navigate(R.id.homeItem,null,NavOptions.Builder().setPopUpTo(R.id.homeItem,true).build())
                }
                true
            }
            homeButton = findViewById(R.id.home_button)
            detailsButton = findViewById(R.id.details_button)
            navView?.menu?.getItem(1)?.isEnabled = false
        }
    }

    override fun onResume() {
        super.onResume()
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_PORTRAIT){
        navView = findViewById(R.id.bottom_nav)
        navView?.selectedItemId = R.id.homeItem}

    }



}