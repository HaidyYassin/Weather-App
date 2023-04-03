package eg.iti.sv.weather

import android.os.Bundle
import android.view.MenuItem
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import androidx.core.content.ContentProviderCompat.requireContext
import androidx.core.view.GravityCompat
import androidx.drawerlayout.widget.DrawerLayout
import androidx.navigation.NavController
import androidx.navigation.Navigation.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.NavigationUI.setupWithNavController
import androidx.navigation.ui.setupWithNavController
import com.google.android.material.navigation.NavigationView
import eg.iti.sv.weather.home.view.CurrentLocation
import eg.iti.sv.weather.home.view.HomeFragment
import eg.iti.sv.weather.models.AppSettings
import eg.iti.sv.weather.utils.checkLanguage
import eg.iti.sv.weather.utils.createAppSettings
import eg.iti.sv.weather.utils.firstTime
import eg.iti.sv.weather.utils.getCustomizedSettings


class HomeActivity : AppCompatActivity(){
    private lateinit var currentLocation: CurrentLocation

    private lateinit var navController: NavController
    private lateinit var drawerLayout: DrawerLayout
    private lateinit var navigationView: NavigationView
    private lateinit var toolbar: Toolbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_home)
        currentLocation = CurrentLocation(this,this)

        drawerLayout = findViewById(R.id.drawer_layout)
        navigationView = findViewById(R.id.navigator)
        toolbar = findViewById(R.id.toolBar)


        setSupportActionBar(toolbar)
        val actionBar = supportActionBar
        actionBar?.setHomeAsUpIndicator(R.drawable.menu_icon)
        actionBar?.setDisplayShowHomeEnabled(true)
        actionBar?.setDisplayHomeAsUpEnabled(true)

        navController = findNavController(this, R.id.nav_host_fragment)
        setupWithNavController(navigationView, navController)

        val appBarConfiguration = AppBarConfiguration(navController.graph,drawerLayout)
        findViewById<Toolbar>(R.id.toolBar)
            .setupWithNavController(navController, appBarConfiguration)
        findViewById<NavigationView>(R.id.navigator)
            .setupWithNavController(navController)


    }

    override fun onOptionsItemSelected(item: MenuItem): Boolean {
        if (item.itemId == android.R.id.home) {
            if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                drawerLayout.closeDrawer(GravityCompat.START)

            } else {
                drawerLayout.openDrawer(GravityCompat.START)
            }
        }
        return super.onOptionsItemSelected(item)
    }

    override fun onResume() {
        super.onResume()
        currentLocation.getLastLocation()
        if(getCustomizedSettings(this) == null){
            createAppSettings(this)
            HomeFragment.appSettings = getCustomizedSettings(this) as AppSettings
        }else{
            println(getCustomizedSettings(this))
            HomeFragment.appSettings = getCustomizedSettings(this) as AppSettings
        }

        if(HomeFragment.appSettings.lang == "Arabic")
            checkLanguage("ar",this)
        else
            checkLanguage("en",this)

    }



}