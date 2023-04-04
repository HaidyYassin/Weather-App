package eg.iti.sv.weather.models

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import eg.iti.sv.weather.MainRule
import eg.iti.sv.weather.db.FakeLocalDataSource
import eg.iti.sv.weather.network.FakeRemoteSource
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.`is`
import org.junit.Assert.*
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.junit.runners.JUnit4

@RunWith(JUnit4::class)
@ExperimentalCoroutinesApi
class RepositoryTest{

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val mainCoroutineRule = MainRule()

    val localSourcePlacesList= mutableListOf(FavPlace("place1",0.0,0.0,"00"),
                                    FavPlace("place2",1.0,1.0,"11"))
    val localSourceAlertsList= mutableListOf(AlertDetails("1","1","1","alarm",pk="1"),
                               AlertDetails("2","2","2","notification",pk="2"))

    val remoteSourceResponse = WeatherResponse(listOf(),
        Current(0,0.0,0,0.0,0,0,0,0,0.0,0.0,0, listOf<Weather>()
        ,0,0.0), listOf(), listOf(),0.0,0.0,"",0)


    lateinit var fakeLocalDataSource: FakeLocalDataSource
    lateinit var fakeRemoteDataSource: FakeRemoteSource
    lateinit var repository: Repository
    lateinit var result : WeatherResponse

    @Before
    fun setUp(){
        fakeLocalDataSource= FakeLocalDataSource(localSourcePlacesList,localSourceAlertsList)
        fakeRemoteDataSource= FakeRemoteSource(remoteSourceResponse)
        repository = Repository.getInstance(fakeRemoteDataSource,fakeLocalDataSource)
    }

    @Test
    fun getWeatherOverNetwork_lonLat_weatherResponse() = mainCoroutineRule.runBlockingTest{

        repository.getWeatherOverNetwork("","").collect{
             result = it
        }
        assertThat(result,`is`(remoteSourceResponse))
    }


    @Test
    fun removePlaceFromFav_place_dbUpdated()=mainCoroutineRule.runBlockingTest {
        //Given
        var result = listOf<FavPlace>()
        val place = FavPlace("place2",1.0,1.0,"11")

        //When
        repository.removePlace(place)
        repository.getAllStoredPlaces().collect{
            result = it
        }

        //Return
        assertThat(result,`is`(listOf(FavPlace("place1",0.0,0.0,"00"))))

    }

    @Test
    fun addPlaceToFav_place_dbUpdated()=mainCoroutineRule.runBlockingTest {
        //Given
        val list = listOf(FavPlace("place1",0.0,0.0,"00"),
            FavPlace("place2",1.0,1.0,"11"),
            FavPlace("place3",3.0,3.0,"33"))
        val place = FavPlace("place3",3.0,3.0,"33")

        //When
        repository.insertPlace(place)

        var result = listOf<FavPlace>()
        repository.getAllStoredPlaces().collect{
            result = it
        }

        //Return
        assertThat(result,`is`(list))

    }
    @Test
    fun getAllFavPlaces_returnFavPlaces()=mainCoroutineRule.runBlockingTest {
        //when
        var result = listOf<FavPlace>()
        repository.getAllStoredPlaces().collect{
            result = it
        }
        //Return
        assertThat(result,`is`(localSourcePlacesList))
    }


    @Test
    fun deleteAlert_alert_dbUpdated()=mainCoroutineRule.runBlockingTest {
        //Given
        var result = listOf<AlertDetails>()
        val alert = AlertDetails("2","2","2","notification",pk="2")

        //When
        repository.deleteAlert(alert)
        repository.getAllAlerts().collect{
            result = it
        }

        //Return
        assertThat(result,`is`(listOf(AlertDetails("1","1","1","alarm",pk="1"))))

    }

    @Test
    fun addAlert_alert_dbUpdated()=mainCoroutineRule.runBlockingTest {
        //Given
        val list = listOf(AlertDetails("1","1","1","alarm",pk="1"),
            AlertDetails("2","2","2","notification",pk="2"),
            AlertDetails("3","3","3","alarm",pk="3"))

        val alert = AlertDetails("3","3","3","alarm",pk="3")

        //When
        repository.insertAlert(alert)

        var result = listOf<AlertDetails>()
        repository.getAllAlerts().collect{
            result = it
        }

        //Return
        assertThat(result,`is`(list))
    }

    @Test
    fun getAllAlerts_returnAlerts()=mainCoroutineRule.runBlockingTest {
        //when
        var result = listOf<AlertDetails>()
        repository.getAllAlerts().collect{
            result = it
        }
        //Return
        assertThat(result,`is`(localSourceAlertsList))
    }
}