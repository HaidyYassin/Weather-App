package eg.iti.sv.weather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import eg.iti.sv.weather.db.AppDataBase
import eg.iti.sv.weather.models.FavPlace
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.CoreMatchers.notNullValue
import org.hamcrest.MatcherAssert
import org.junit.After
import org.junit.Assert.assertThat
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first


@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class FavDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    private lateinit var database: AppDataBase

    @Before
    fun setUp(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(),AppDataBase::class.java
        ).build()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun savePlace_place_retrievesPlace()= runBlockingTest{
        //Given
        val place = FavPlace("place1",0.0,0.0,"00")
        database.getFavPlaceDao().insertPlace(place)

        //When
        val retrievedTask = database.getFavPlaceDao().findPlaceById(place.latLog)

        //Return
        MatcherAssert.assertThat<FavPlace>(retrievedTask as FavPlace, CoreMatchers.notNullValue())
        MatcherAssert.assertThat(retrievedTask.latLog, CoreMatchers.`is`(place.latLog))

    }

    @Test
    fun deletePlace_place_dbUpdated()= runBlockingTest{
        //Given
        val place1 = FavPlace("place1",0.0,0.0,"00")
        val place2 = FavPlace("place12",02.0,02.0,"22")
        database.getFavPlaceDao().insertPlace(place1)
        database.getFavPlaceDao().insertPlace(place2)


        //When
        database.getFavPlaceDao().deletePlace(place1)
        val retrievedTask = database.getFavPlaceDao().findPlaceById(place1.latLog)

        //Return
        MatcherAssert.assertThat<FavPlace>(retrievedTask , CoreMatchers.nullValue())

    }

    @Test
    fun getAllFavPlaces_returnAllStoredPlaces()= runBlockingTest{
        //Given
        val place1 = FavPlace("place1",0.0,0.0,"00")
        val place2 = FavPlace("place12",02.0,02.0,"22")
        val list = listOf(FavPlace("place1",0.0,0.0,"00"),
            FavPlace("place12",02.0,02.0,"22"))

        database.getFavPlaceDao().insertPlace(place1)
        database.getFavPlaceDao().insertPlace(place2)


        var result = listOf<FavPlace>()
        //When
        result = database.getFavPlaceDao().allFavPlaces.first()
        //Return
        assertThat(result , notNullValue())
        assertThat(result,`is`(list))

    }
}