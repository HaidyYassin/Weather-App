package eg.iti.sv.weather

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import androidx.room.Room
import androidx.test.core.app.ApplicationProvider
import androidx.test.ext.junit.runners.AndroidJUnit4
import androidx.test.filters.SmallTest
import eg.iti.sv.weather.db.AppDataBase
import eg.iti.sv.weather.models.AlertDetails
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.test.runBlockingTest
import org.hamcrest.CoreMatchers.*
import org.hamcrest.MatcherAssert.assertThat
import org.junit.*
import org.junit.runner.RunWith

@ExperimentalCoroutinesApi
@RunWith(AndroidJUnit4::class)
@SmallTest
class AlertsDaoTest {

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()


    private lateinit var database: AppDataBase

    @Before
    fun setUp(){
        database = Room.inMemoryDatabaseBuilder(
            ApplicationProvider.getApplicationContext(), AppDataBase::class.java
        ).build()
    }

    @After
    fun tearDown(){
        database.close()
    }

    @Test
    fun saveAlert_alert_dpUpdated()= runBlockingTest{
        //Given
        val alert1 = AlertDetails("1","1","1","alarm",pk="1")

        //When
        database.getAlertsDao().insertAlert(alert1)
        val result = database.getAlertsDao().allAlerts.first()

        //Return
        assertThat(result, notNullValue())
    }

    @Test
    fun deleteAlert_alert_dbUpdated()= runBlockingTest{
        //Given
        val alert1 = AlertDetails("1","1","1","alarm",pk="1")
        val alert2 = AlertDetails("2","2","2","notification",pk="2")

        database.getAlertsDao().insertAlert(alert1)
        //database.getAlertsDao().insertAlert(alert2)


        //When
        database.getAlertsDao().deleteAlert(alert1)
        val result = database.getAlertsDao().allAlerts.first()

        //Return
       assertThat(result , `is`(listOf()))

    }

    @Test
    fun getAllAlerts_returnAllStoredAlerts()= runBlockingTest{
        //Given
        val alert1 = AlertDetails("1","1","1","alarm",pk="1")
        val alert2 = AlertDetails("2","2","2","notification",pk="2")
        val list = listOf( AlertDetails("1","1","1","alarm",pk="1")
            , AlertDetails("2","2","2","notification",pk="2")
        )

        database.getAlertsDao().insertAlert(alert1)
        database.getAlertsDao().insertAlert(alert2)

        var result = listOf<AlertDetails>()
        //When
        result = database.getAlertsDao().allAlerts.first()
        //Return
        Assert.assertThat(result , notNullValue())
        Assert.assertThat(result, `is`(list))

    }
}