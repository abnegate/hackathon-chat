package io.appwrite.messagewrite

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.navigation.fragment.findNavController
import androidx.navigation.ui.AppBarConfiguration
import androidx.navigation.ui.setupActionBarWithNavController
import com.google.android.material.chip.Chip
import com.google.android.material.chip.ChipGroup
import dagger.hilt.android.AndroidEntryPoint
import io.appwrite.messagewrite.databinding.ActivityMainBinding


@AndroidEntryPoint
class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        binding = ActivityMainBinding.inflate(layoutInflater)

        setContentView(binding.root)

        val navController = supportFragmentManager
            .findFragmentById(R.id.nav_host_fragment_activity_main)
            ?.findNavController()

        val appBarConfiguration = AppBarConfiguration(
            setOf(
                R.id.navigation_chats,
                R.id.navigation_contacts,
                R.id.navigation_settings
            )
        )

        setupActionBarWithNavController(navController!!, appBarConfiguration)

        val chipGroup = findViewById<ChipGroup>(R.id.nav_view)
        chipGroup.setOnCheckedChangeListener { group, checkedId ->
            if (checkedId == -1) {
                return@setOnCheckedChangeListener
            }

            navController.navigate(checkedId)
        }
    }
}