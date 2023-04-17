package ba.unsa.etf.rpr.rma23_19146_spirala1


import android.content.res.Configuration
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.fragment.app.commit
import androidx.fragment.app.replace
import androidx.navigation.NavArgument
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.google.android.material.bottomnavigation.BottomNavigationView

class HomeFragment : Fragment() {
    private lateinit var gameList : RecyclerView
    private lateinit var gameListAdapter: GameListAdapter
    private var games = GameData.getAll()

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        val view =  inflater.inflate(R.layout.home_fragment,container,false)
        gameList = view.findViewById(R.id.game_list)
        gameList.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        gameListAdapter = GameListAdapter(arrayListOf()){game -> showGameDetails(game)}
        gameList.adapter = gameListAdapter
        gameListAdapter.updateGames(games)
        return view
    }



    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
    private fun showGameDetails(game:Game){
        val orientation = resources.configuration.orientation
        (activity as HomeActivity).gameTitle = game.title
        /**
         * Ovaj kood mijenja desni fragment na igru koja se klikne na lijevom u landscape prikazu
         */
        if (orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            val frag = (activity as HomeActivity).supportFragmentManager
            frag.commit {
                replace<GameDetailFragment>(R.id.nav_host_fragment_container_second)
                setReorderingAllowed(false)
                addToBackStack(null)
            }

        }
        else{
            (activity as HomeActivity).navView.menu.getItem(1).isEnabled = true
            val bundle = bundleOf("game_title" to game.title)
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_gameDetailFragment,bundle)
        }
    }

}