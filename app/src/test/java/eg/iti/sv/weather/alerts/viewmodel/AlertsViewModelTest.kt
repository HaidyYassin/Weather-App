package eg.iti.sv.weather.alerts.viewmodel

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import eg.iti.sv.weather.MainRule
import eg.iti.sv.weather.getOrAwaitValue
import eg.iti.sv.weather.map.view.viewModel
import eg.iti.sv.weather.models.AlertDetails
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
class AlertsViewModelTest{

    private lateinit var alertsViewModel: AlertsViewModel
    private lateinit var repo: FakeRepository

    @get:Rule
    val mainCoroutineRule = MainRule()

    @get:Rule
    val instantTaskExecutorRule = InstantTaskExecutorRule()

    @Before
    fun setUp(){
        repo = FakeRepository()
        alertsViewModel = AlertsViewModel(repo)
    }

    @Test
    fun getAllAlerts_returnAlertsFromDB(){

        //When
        val result = alertsViewModel.alerts.getOrAwaitValue {  }

        //Return
        assertThat(result, notNullValue())
    }

    @Test
    fun removeAlert_alert_dbUpdated ()= mainCoroutineRule.runBlockingTest{
        //Given
        repo.insertAlert(AlertDetails("1","1","1","alarm",pk="1"))
        repo.insertAlert(AlertDetails("2","2","2","notification",pk="2"))
        repo.insertAlert(AlertDetails("3","3","3","notification",pk="3"))


        //When
        val alert = AlertDetails("2","2","2","notification",pk="2")
        alertsViewModel.removeAlert(alert)

        val result = alertsViewModel.alerts.getOrAwaitValue {  }
        val list = listOf(AlertDetails("1","1","1","alarm",pk="1"),
            AlertDetails("3","3","3","notification",pk="3"))

        //Return
        assertThat(result, notNullValue())
        assertThat(result,`is`(list))

    }

    @Test
    fun addAlert_alert_dbUpdated() = mainCoroutineRule.runBlockingTest{

        //Given
        val list = listOf(AlertDetails("1","1","1","alarm",pk="1"),
            AlertDetails("2","2","2","notification",pk="2"))

        val alert1 = AlertDetails("1","1","1","alarm",pk="1")
        val alert2 = AlertDetails("2","2","2","notification",pk="2")

        //When
        alertsViewModel.addAlert(alert1)
        alertsViewModel.addAlert(alert2)

        val result = alertsViewModel.alerts.getOrAwaitValue {  }

        //Return
        assertThat(result, notNullValue())
        assertThat(result,`is`(list))
    }

}