package com.consumer.content.provider.glue.humans

import androidx.compose.runtime.Composable
import androidx.compose.ui.platform.LocalContext
import com.consumer.content.humans.presentation.details.viewmodel.HumanDetailsViewModel
import com.consumer.content.provider.MainActivity

@Composable
fun requiredHumanDetailsViewModelFactory(): HumanDetailsViewModel.Factory {
    val context = LocalContext.current

    return (context as MainActivity).provideViewModelFactory()
}