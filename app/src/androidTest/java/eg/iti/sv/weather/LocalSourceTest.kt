package eg.iti.sv.weather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.MediumTest
import eg.iti.sv.weather.db.AppDataBase
import eg.iti.sv.weather.db.ConcreteLocalSource
import eg.iti.sv.weather.models.AlertDetails
import eg.iti.sv.weather.models.FavPlace
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.*
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@MediumTest
class LocalSourceTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @get:Rule
    val rule = MainRule()

    private lateinit var database: AppDataBase
    private lateinit var localDataSource: ConcreteLocalSource

    @Before
    fun setUp(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),AppDataBase::class.java
        ).allowMainThreadQueries()
            .build()

        localDataSource = ConcreteLocalSource(ApplicationProvider.getApplicationContext())
    }

    @After
    fun tearDown(){
        database.close()

    }

    @Test
    fun savePlace_dpUpdated()= rule.runBlockingTest{

        //Given
        val list = listOf(
            FavPlace("place1",0.0,0.0,"00"),
            FavPlace("place2",1.0,1.0,"11")
        )
        val place1 = FavPlace("place1",0.0,0.0,"00")
        val place2 = FavPlace("place2",1.0,1.0,"11")


        //When

        localDataSource.insertPlace(place1)
        localDataSource.insertPlace(place2)

        var result = listOf<FavPlace>()
        result=localDataSource.getAllFavPlaces().first()
        //Return
        Assert.assertThat(result, CoreMatchers.`is`(list))

    }

    @Test
    fun removePlace_dpUpdated()= rule.runBlockingTest{

        //Given
        val place1 = FavPlace("place1",0.0,0.0,"00")
        val place2 = FavPlace("place2",1.0,1.0,"11")
        localDataSource.insertPlace(place1)
        localDataSource.insertPlace(place2)

        var result = listOf<FavPlace>()
        val place = FavPlace("place2",1.0,1.0,"11")

        //When
        localDataSource.deletePlace(place)
        result = localDataSource.getAllFavPlaces().first()
        //Return
        Assert.assertThat(result, CoreMatchers.`is`(listOf(FavPlace("place1", 0.0, 0.0, "00"))))

    }

    @Test
    fun getAllFavPlaces_returnFavPlaces()=rule.runBlockingTest {
        //Given
        val list = listOf(
            FavPlace("place1",0.0,0.0,"00"),
            FavPlace("place2",1.0,1.0,"11")
        )
        val place1 = FavPlace("place1",0.0,0.0,"00")
        val place2 = FavPlace("place2",1.0,1.0,"11")
        localDataSource.insertPlace(place1)
        localDataSource.insertPlace(place2)

        //when

        var result = listOf<FavPlace>()
        result = localDataSource.getAllFavPlaces().first()
        //Return

        assertThat(result, CoreMatchers.`is`(list))
    }

    @Test
    fun deleteAlert_alert_dbUpdated()=rule.runBlockingTest {
        //Given

        var result = listOf<AlertDetails>()
        val alert1 =AlertDetails("1", "1", "1", "alarm", pk = "1")
        val alert2 = AlertDetails("2","2","2","notification",pk="2")

        localDataSource.insertAlert(alert1)
        localDataSource.insertAlert(alert2)

        //When
       localDataSource.deleteAlert(alert2)
        result = localDataSource.getAllAlerts().first()
        //Return
        Assert.assertThat(
            result,
            CoreMatchers.`is`(listOf(AlertDetails("1", "1", "1", "alarm", pk = "1")))
        )

    }

    @Test
    fun addAlert_alert_dbUpdated()=rule.runBlockingTest {
        //Given
        val list = listOf(AlertDetails("1","1","1","alarm",pk="3"),
            AlertDetails("1","1","1","alarm",pk="1"))
        val alert = AlertDetails("1","1","1","alarm",pk="1")

        //When
        localDataSource.insertAlert(alert)

        var result = listOf<AlertDetails>()
        result = localDataSource.getAllAlerts().first()

        //Return
        assertThat(result, `is`(list))
    }

    @Test
    fun getAllAlerts_returnAlerts()=rule.runBlockingTest {

        //Given
        val list = listOf(AlertDetails("3","3","3","alarm",pk="3"),
            AlertDetails("1","1","1","alarm",pk="1"))
        val alert1 = AlertDetails("1","1","1","alarm",pk="1")
        val alert3 = AlertDetails("3","3","3","alarm",pk="3")

        //When
        localDataSource.insertAlert(alert3)
        localDataSource.insertAlert(alert1)

        var result = listOf<AlertDetails>()
        result = localDataSource.getAllAlerts().first()
        //Return
        Assert.assertThat(result, `is`(list))
    }


}