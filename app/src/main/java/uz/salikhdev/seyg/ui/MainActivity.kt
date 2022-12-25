package uz.salikhdev.seyg.ui

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.NavHostFragment
import uz.salikhdev.seyg.R

class MainActivity : AppCompatActivity() {
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        val navHost = supportFragmentManager.findFragmentById(R.id.navHost) as NavHostFragment
        val graph = navHost.navController.navInflater.inflate(R.navigation.main_navigation)
        navHost.navController.graph = graph

    }

}