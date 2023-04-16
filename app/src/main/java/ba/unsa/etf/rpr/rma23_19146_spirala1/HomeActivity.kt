package ba.unsa.etf.rpr.rma23_19146_spirala1

import android.content.Intent
import android.content.res.Configuration
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.util.Log
import android.view.View
import android.widget.Button
import android.widget.ImageButton
import androidx.core.os.bundleOf
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.NavArgument
import androidx.navigation.findNavController
import androidx.navigation.fragment.NavHostFragment
import androidx.navigation.ui.setupWithNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeActivity : AppCompatActivity() {
    private lateinit var homeButton: Button
    private lateinit var gameList: RecyclerView
    private lateinit var gameListAdapter: GameListAdapter
    private var games = GameData.getAll()
    private lateinit var detailsButton: Button
    var gameTitle: String? = GameData.getAll()[0].title
    lateinit var navView: BottomNavigationView

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.home_activity)
        val navHostFragment =
            supportFragmentManager.findFragmentById(R.id.nav_host_fragment_container) as NavHostFragment
        val navController = navHostFragment.navController
        navView = findViewById(R.id.bottom_nav)
        navView.setupWithNavController(navController)
        val orientation = resources.configuration.orientation
        if (orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            navController.navigate(R.id.homeItem)
            navView.visibility = View.GONE
        }
        else{
            navView.visibility = View.VISIBLE
            navView.menu.getItem(1).isEnabled=false
        }
        navView.setOnItemSelectedListener { it ->
            if (it.itemId == R.id.gameDetailsItem) {
                val bundle = bundleOf("game_title" to gameTitle)
                navController.navigate(R.id.gameDetailsItem, bundle)
            }
            if (it.itemId == R.id.homeItem) {
                navController.navigate(R.id.homeItem)
            }
            true
        }
        homeButton = findViewById(R.id.home_button)
        detailsButton = findViewById(R.id.details_button)
        detailsButton.isEnabled = false
    }


}