package com.consumer.content.humans.presentation.details

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults.topAppBarColors
import androidx.compose.runtime.Composable
import androidx.compose.runtime.State
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import com.consumer.content.core.presentation.views.AppErrorView
import com.consumer.content.core.presentation.views.PullToRefreshBox
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

    val refreshState = viewModel.refreshState
        .collectAsStateWithLifecycle(false)

    HumanDetailsView(
        uiState = uiState,
        refreshState = refreshState,
    )
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun HumanDetailsView(
    uiState: State<HumanDetailsUiState>,
    refreshState: State<Boolean>,
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
        PullToRefreshBox(
            isRefreshing = refreshState.value,
            onRefresh = {
            },
            modifier = Modifier
                .fillMaxSize()
                .padding(innerPadding),
        ) {
            when(val state = uiState.value) {
                is HumanDetailsUiState.Data -> HumanDetailsView(state)
                HumanDetailsUiState.Empty -> {}
                is HumanDetailsUiState.Error -> AppErrorView(
                    onTryAgain = { }
                )
                HumanDetailsUiState.Pending -> {}
                HumanDetailsUiState.Pure -> {}
            }
        }
    }
}

@Composable
@Preview(showSystemUi = true)
fun HumanDetailPreview() {

}