package ba.etf.rma23.projekat


import android.content.res.Configuration
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.Button
import android.widget.EditText
import android.widget.Toast
import androidx.core.os.bundleOf
import androidx.fragment.app.Fragment
import androidx.navigation.NavOptions
import androidx.navigation.findNavController
import androidx.navigation.fragment.findNavController
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import ba.etf.rma23.projekat.data.repositories.AccountApiConfig
import ba.etf.rma23.projekat.data.repositories.IGDBApiConfig
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch
import kotlin.collections.ArrayList

class HomeFragment : Fragment() {
    private lateinit var gameList : RecyclerView
    lateinit var gameListAdapter: GameListAdapter
    private lateinit var searchButton : Button
    private lateinit var searchText :EditText
    private var games = GameData.initialGames
    private lateinit var filterButton : Button
    private lateinit var setAgeButton : Button
    private lateinit var favoritesButton: Button
    private lateinit var filterFavoritesButton: Button
    private lateinit var ageText : EditText
    private lateinit var sortButton: Button
    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View?{
        val view =  inflater.inflate(R.layout.home_fragment,container,false)
        gameList = view.findViewById(R.id.game_list)
        searchButton = view.findViewById(R.id.search_button)
        favoritesButton=view.findViewById(R.id.favorites_button)
        filterFavoritesButton=view.findViewById(R.id.filter_favorites_button)
        filterButton = view.findViewById(R.id.filter_button)
        sortButton = view.findViewById(R.id.sort_button)
        setAgeButton=view.findViewById(R.id.set_age_button)
        ageText = view.findViewById(R.id.age_edittext)
        searchText = view.findViewById(R.id.search_query_edittext)
        gameList.layoutManager = LinearLayoutManager(context,LinearLayoutManager.VERTICAL,false)
        gameListAdapter = GameListAdapter(arrayListOf()){game -> showGameDetails(game)}
        gameList.adapter = gameListAdapter
        gameListAdapter.updateGames(GameData.initialGames)
        searchButton.setOnClickListener {
            getGamesByName(searchText.text.toString())
        }
        filterButton.setOnClickListener {
            val scope = CoroutineScope(Job() + Dispatchers.Main)
            scope.launch {
                IGDBApiConfig.GamesRepository.getGamesSafe(searchText.text.toString())
                    ?.let { it1 -> gameListAdapter.updateGames(it1) }
            }
        }
        setAgeButton.setOnClickListener {
            val age : Int = if(ageText.text.toString() != "") ageText.text.toString().toInt() else -1
            if(AccountApiConfig.AccountGamesRepository.setAge(age))
                Toast.makeText(context,"Age set!",Toast.LENGTH_SHORT).show()
            else
                Toast.makeText(context,"Invalid age!",Toast.LENGTH_SHORT).show()

        }
        /**
         * Ukoliko ima nešta u search baru, klik na favoritesButton će prikazati omiljene igre čije
         * ime sadrži taj string
         */
        favoritesButton.setOnClickListener {
            val scope = CoroutineScope(Job() + Dispatchers.Main)
            scope.launch {
                var gamesList : List<Game> = AccountApiConfig.AccountGamesRepository.getSavedGames()
                GameData.favoriteGames = gamesList
                if (searchText.text.toString() != ""){
                    gameListAdapter.updateGames(AccountApiConfig.AccountGamesRepository.getGamesContainingString(searchText.text.toString()))
                    Toast.makeText(context,"Omiljene igre za query: ${searchText.text.toString()}",Toast.LENGTH_SHORT).show()
                }
                else{
                    gameListAdapter.updateGames(gamesList)
                }
            }
        }
        sortButton.setOnClickListener {
            val scope = CoroutineScope(Job() + Dispatchers.Main)
            scope.launch {
                gameListAdapter.updateGames(IGDBApiConfig.GamesRepository.sortGames())
            }

        }
        filterFavoritesButton.setOnClickListener {
            val scope = CoroutineScope(Job() + Dispatchers.Main)
            scope.launch {
                if(AccountApiConfig.AccountGamesRepository.removeNonSafe()) {
                    Toast.makeText(
                        context,
                        "Age-restricted games removed",
                        Toast.LENGTH_SHORT
                    ).show()
                    gameListAdapter.updateGames(AccountApiConfig.AccountGamesRepository.getSavedGames())
                }
                else Toast.makeText(
                    context,
                    "Age not set",
                    Toast.LENGTH_SHORT
                ).show()

            }
        }
        return view
    }


    companion object {
        fun newInstance(): HomeFragment = HomeFragment()
    }
    fun getGamesByName(q:String){
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        scope.launch {
            val result = IGDBApiConfig.GamesRepository.getGamesByName(q)
            when (result){
                is List<Game> -> onSuccess(result)
                else -> onError()
            }
        }
    }
    fun onSuccess(games: List<Game>){
        gameListAdapter.updateGames(games)
        GameData.initialGames = games as ArrayList<Game>
    }
    fun onError() {
        val toast = Toast.makeText(context, "Search error", Toast.LENGTH_SHORT)
        toast.show()
    }

    private fun showGameDetails(game:Game){
        val orientation = resources.configuration.orientation
        (activity as MainActivity).gameTitle = game.title
        val scope = CoroutineScope(Job() + Dispatchers.Main)
        /*scope.launch {
            Toast.makeText(context,AccountApiConfig.AccountGamesRepository.getSavedGames().get(0).description,Toast.LENGTH_SHORT).show()
        }*/
        /**
         * Ovaj kood mijenja desni fragment na igru koja se klikne na lijevom u landscape prikazu
         */
        if (orientation == Configuration.ORIENTATION_LANDSCAPE)
        {
            val frag = (activity as MainActivity).supportFragmentManager
            val navHostFragment = frag.findFragmentById(R.id.nav_host_fragment_container_second)
            navHostFragment?.findNavController()
                ?.navigate(R.id.gameDetailFragment,null,NavOptions.Builder().setPopUpTo(R.id.homeItem,true).build())
        }
        else{
            (activity as MainActivity).navView?.menu?.getItem(1)?.isEnabled = true
            val bundle = bundleOf("game_title" to game.title)
            view?.findNavController()?.navigate(R.id.action_homeFragment_to_gameDetailFragment,bundle)
        }
    }

}