package ba.etf.rma23.projekat

import android.content.pm.ActivityInfo
import androidx.recyclerview.widget.RecyclerView
import androidx.test.espresso.Espresso
import androidx.test.espresso.Espresso.pressBack
import androidx.test.espresso.NoMatchingViewException
import androidx.test.espresso.action.ViewActions
import androidx.test.espresso.assertion.PositionAssertions
import androidx.test.espresso.assertion.ViewAssertions
import androidx.test.espresso.contrib.RecyclerViewActions
import androidx.test.espresso.matcher.ViewMatchers
import androidx.test.ext.junit.rules.ActivityScenarioRule
import androidx.test.ext.junit.runners.AndroidJUnit4
import org.hamcrest.CoreMatchers
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith

@RunWith(AndroidJUnit4::class)
class OwnEspressoTests {
    @get:Rule
    var homeRule: ActivityScenarioRule<MainActivity> = ActivityScenarioRule(MainActivity::class.java)
    /**
     * Korisniku treba biti omogućen prikaz posljednje otvorene igre kada klikne na details dugme u
     * BottomNavigationMenu
     * U landscape orijentaciji, potrebno je da se kliknuta igra pojavi na ekranu
     */
    @Test
    fun detailsBottomNavNakonClick(){
        var prvaIgra = GameData.getAll().get(0)
        homeRule.scenario.onActivity { activity -> activity.requestedOrientation =
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT}
        Espresso.onView(ViewMatchers.withId(R.id.game_list)).perform(
            RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                CoreMatchers.allOf(
                    ViewMatchers.hasDescendant(ViewMatchers.withText(prvaIgra.title)),
                    ViewMatchers.hasDescendant(ViewMatchers.withText(prvaIgra.releaseDate)),
                    ViewMatchers.hasDescendant(ViewMatchers.withText(prvaIgra.rating.toString()))
                ), ViewActions.click()
            ))
        Espresso.onView(ViewMatchers.withId(R.id.homeItem)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withId(R.id.gameDetailsItem)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText(prvaIgra.description))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        Espresso.onView(ViewMatchers.withId(R.id.homeItem)).perform(ViewActions.click())
        var drugaIgra = GameData.getAll()[1]
        homeRule.scenario.onActivity { activity -> activity.requestedOrientation =
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE}
        Espresso.onView(ViewMatchers.withId(R.id.game_list)).perform(
            RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                CoreMatchers.allOf(
                    ViewMatchers.hasDescendant(ViewMatchers.withText(drugaIgra.title)),
                    ViewMatchers.hasDescendant(ViewMatchers.withText(drugaIgra.releaseDate)),
                    ViewMatchers.hasDescendant(ViewMatchers.withText(drugaIgra.rating.toString()))
                ), ViewActions.click()
            ))
        Espresso.onView(ViewMatchers.withText(drugaIgra.description))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    /**
     * Aplikacija treba sačuvati zadnju sačuvanu igru i u slučaju da je pritisnut back button nakon
     * otvaranja igre, također u ovom testu testiramo novi layout game_details_activity, odnosno
     * game_details_fragment
     */
    @Test
    fun detailsBottomNavNakonBackILayout(){
        var prvaIgra = GameData.getAll().get(0)
        homeRule.scenario.onActivity { activity -> activity.requestedOrientation =
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT}
        Espresso.onView(ViewMatchers.withId(R.id.game_list)).perform(
            RecyclerViewActions.actionOnItem<RecyclerView.ViewHolder>(
                CoreMatchers.allOf(
                    ViewMatchers.hasDescendant(ViewMatchers.withText(prvaIgra.title)),
                    ViewMatchers.hasDescendant(ViewMatchers.withText(prvaIgra.releaseDate)),
                    ViewMatchers.hasDescendant(ViewMatchers.withText(prvaIgra.rating.toString()))
                ), ViewActions.click()
            ))
        Espresso.onView(ViewMatchers.withId(R.id.cover_imageview))
            .check(PositionAssertions.isCompletelyLeftOf(ViewMatchers.withId(R.id.linearLayout)))
        Espresso.onView(ViewMatchers.withId(R.id.cover_imageview))
            .check(PositionAssertions.isCompletelyLeftOf(ViewMatchers.withId(R.id.linearLayout2)))
        Espresso.onView(ViewMatchers.withId(R.id.linearLayout))
            .check(PositionAssertions.isCompletelyAbove(ViewMatchers.withId(R.id.linearLayout2)))
        Espresso.onView(ViewMatchers.withId(R.id.linearLayout2))
            .check(PositionAssertions.isCompletelyAbove(ViewMatchers.withId(R.id.description_scrollview)))
        Espresso.onView(ViewMatchers.withId(R.id.cover_imageview))
            .check(PositionAssertions.isCompletelyLeftOf(ViewMatchers.withId(R.id.description_textview)))
        Espresso.onView(ViewMatchers.withId(R.id.cover_imageview))
            .check(PositionAssertions.isCompletelyAbove(ViewMatchers.withId(R.id.impression_recyclerview)))
        Espresso.onView(ViewMatchers.withId(R.id.description_scrollview))
            .check(PositionAssertions.isCompletelyAbove(ViewMatchers.withId(R.id.impression_recyclerview)))
        Espresso.onView(ViewMatchers.withId(R.id.item_title_textview))
            .check(PositionAssertions.isCompletelyAbove(ViewMatchers.withId(R.id.cover_imageview)))
        Espresso.onView(ViewMatchers.withId(R.id.item_title_textview))
            .check(PositionAssertions.isCompletelyAbove(ViewMatchers.withId(R.id.linearLayout)))
        pressBack()
        Espresso.onView(ViewMatchers.withId(R.id.gameDetailsItem)).perform(ViewActions.click())
        Espresso.onView(ViewMatchers.withText(prvaIgra.description))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
    }

    /**
     * Kada korisnik promijeni orijentaciju ekrana u landscape, trebaju mu biti vidljive relevantne informacije za
     * prvu igru u game_list.
     */
    @Test
    fun portraitToLandscape(){
        var prvaIgra = GameData.getAll().get(0)
        homeRule.scenario.onActivity { activity -> activity.requestedOrientation =
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE}
        Espresso.onView(ViewMatchers.withText(prvaIgra.description))
            .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        try {
            Espresso.onView(ViewMatchers.withId(R.id.bottom_nav))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }catch (e : NoMatchingViewException){

        }
    }

    /**
     * Ukoliko je korisnik na početnom ekranu, details dugme treba biti onemogućeno
     * Isto tako, ukoliko je u landscape orijentaciji, bottom navigation ne smije biti vidljiv
     */
    @Test
    fun testBottomNav(){
        homeRule.scenario.onActivity { activity -> activity.requestedOrientation =
            ActivityInfo.SCREEN_ORIENTATION_PORTRAIT}
        Espresso.onView(ViewMatchers.withId(R.id.gameDetailsItem)).check(ViewAssertions.matches(ViewMatchers.isNotEnabled()))
        Espresso.onView(ViewMatchers.withId(R.id.bottom_nav))
            .check(ViewAssertions.matches((ViewMatchers.isDisplayed())))
        homeRule.scenario.onActivity { activity -> activity.requestedOrientation =
            ActivityInfo.SCREEN_ORIENTATION_LANDSCAPE}
        try {
            Espresso.onView(ViewMatchers.withId(R.id.bottom_nav))
                .check(ViewAssertions.matches(ViewMatchers.isDisplayed()))
        }catch (e : NoMatchingViewException){

        }
    }

}