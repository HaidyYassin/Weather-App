package eg.iti.sv.weather.fav.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import eg.iti.sv.weather.MainRule
import eg.iti.sv.weather.getOrAwaitValue
import eg.iti.sv.weather.models.FakeRepository
import eg.iti.sv.weather.models.FavPlace
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.*
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class FavViewModelTest{

    private lateinit var favViewModel: FavViewModel
    private lateinit var repo:FakeRepository

    @get:Rule
    val mainCoroutineRule = MainRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        repo = FakeRepository()
        favViewModel = FavViewModel(repo)

    }

    @Test
     fun getAllFAv_returnFavPlacesFromDB(){

        //When
        val result = favViewModel.places.getOrAwaitValue { }

       //Return
        assertThat(result, notNullValue())
    }

    @Test
     fun removePlace_place_dbUpdated ()= mainCoroutineRule.runBlockingTest{
        //Given
        repo.insertPlace(FavPlace("place1",0.0,0.0,"00"))
        repo.insertPlace(FavPlace("place2",1.0,1.0,"11"))

        val favList= listOf(FavPlace("place1",0.0,0.0,"00"))

        //When
        val place = FavPlace("place2",1.0,1.0,"11")
        favViewModel.removeFromFav(place)

        val result =favViewModel.places.getOrAwaitValue {  }

        //Return
        assertThat(result, notNullValue())
        assertThat(result,`is`(favList))

    }


}