package com.consumer.content.provider

import android.os.Bundle
import androidx.activity.ComponentActivity
import androidx.activity.compose.setContent
import androidx.activity.enableEdgeToEdge
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.ui.Modifier
import androidx.navigation.compose.rememberNavController
import com.consumer.content.core.theme.ConsumerContentProviderTheme
import com.consumer.content.humans.presentation.details.viewmodel.HumanDetailsViewModel
import com.consumer.content.provider.glue.navigation.AppNavHost
import dagger.hilt.EntryPoint
import dagger.hilt.InstallIn
import dagger.hilt.android.AndroidEntryPoint
import dagger.hilt.android.EntryPointAccessors
import dagger.hilt.android.components.ActivityComponent

@AndroidEntryPoint
class MainActivity : ComponentActivity(), HumanDetailsViewModel.FactoryProvider {

    @EntryPoint
    @InstallIn(ActivityComponent::class)
    interface ViewModelFactoryProvider {
        fun humanDetailsViewModelFactory(): HumanDetailsViewModel.Factory
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        enableEdgeToEdge()

        setContent {
            ConsumerContentProviderTheme {
                Surface(
                    modifier = Modifier.fillMaxSize(),
                    color = MaterialTheme.colorScheme.background,
                ) {
                    AppNavHost(navController = rememberNavController())
                }
            }
        }
    }

    override fun provideViewModelFactory(): HumanDetailsViewModel.Factory {
        return EntryPointAccessors.fromActivity(
            this,
            ViewModelFactoryProvider::class.java,
        ).humanDetailsViewModelFactory()
    }

}