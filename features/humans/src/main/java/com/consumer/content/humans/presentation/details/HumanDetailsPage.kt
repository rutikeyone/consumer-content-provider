package com.consumer.content.humans.presentation.details

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.consumer.content.core.presentation.views.AppErrorView
import com.consumer.content.core.presentation.views.AppProgressIndicator
import com.consumer.content.humans.R
import com.consumer.content.humans.presentation.details.viewmodel.HumanDetailsUiState
import com.consumer.content.humans.presentation.details.viewmodel.HumanDetailsViewModel
import com.consumer.content.humans.presentation.details.views.HumanDetailsView

@Composable
fun HumanDetailsPage(
    viewModel: HumanDetailsViewModel,
) {

    val uiState = viewModel.uiState
        .collectAsStateWithLifecycle(HumanDetailsUiState.Pending)

    HumanDetailsView(uiState)
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HumanDetailsView(
    uiState: State<HumanDetailsUiState>,
) {
    Scaffold(
        topBar = {
            TopAppBar(
                colors = topAppBarColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    titleContentColor = MaterialTheme.colorScheme.primary,
                ),
                title = {
                    Text(stringResource(R.string.details))
                },
            )
        },
    ) { innerPadding ->
        Box(
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            when (val state = uiState.value) {
                is HumanDetailsUiState.Data -> HumanDetailsView(state)
                HumanDetailsUiState.Empty -> {}
                is HumanDetailsUiState.Error -> AppErrorView()
                HumanDetailsUiState.Pending -> AppProgressIndicator()
                HumanDetailsUiState.Pure -> {}
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun HumanDetailPreview() {

}