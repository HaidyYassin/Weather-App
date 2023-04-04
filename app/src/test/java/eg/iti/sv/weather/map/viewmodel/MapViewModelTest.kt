package eg.iti.sv.weather.map.viewmodel


import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import eg.iti.sv.weather.MainRule
import eg.iti.sv.weather.models.FakeRepository
import eg.iti.sv.weather.models.FavPlace
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

/*@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class MapViewModelTest{

    private lateinit var mapViewModel: MapViewModel
    private lateinit var repo: FakeRepository

    @get:Rule
    val mainCoroutineRule = MainRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        repo = FakeRepository()
        mapViewModel = MapViewModel(repo)

    }

    @Test
    fun addFavPlace_place_dbUpdated() = mainCoroutineRule.runBlockingTest{

        //Given
        val list = listOf(
            FavPlace("place1",0.0,0.0,"00"),
            FavPlace("place2",1.0,1.0,"11")
        )

        val place1 = FavPlace("place1",0.0,0.0,"00")
        val place2 = FavPlace("place2",1.0,1.0,"11")

        //When
        mapViewModel.addPlaceToFav(place1)
        mapViewModel.addPlaceToFav(place2)

        val result = repo.places
        assertThat(result, CoreMatchers.`is`(list))
    }

}*/