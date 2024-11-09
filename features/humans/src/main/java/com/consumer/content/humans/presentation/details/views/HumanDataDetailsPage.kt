package com.consumer.content.humans.presentation.details.views

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.verticalScroll
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.ImeAction
import androidx.compose.ui.unit.dp
import com.consumer.content.core.presentation.views.AppTextField
import com.consumer.content.humans.R
import com.consumer.content.humans.presentation.details.viewmodel.HumanDetailsUiState

@Composable
fun HumanDetailsView(
    state: HumanDetailsUiState.Data,
) {
    Column(
        modifier = Modifier
            .fillMaxSize()
            .verticalScroll(rememberScrollState()),
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally
    ) {
        AppTextField(
            modifier = Modifier
                .width(240.dp)
                .padding(top = 16.dp),
            label = stringResource(R.string.id),
            value = state.human.id.toString(),
            readOnly = true,
        )
        AppTextField(
            modifier = Modifier
                .width(240.dp)
                .padding(top = 16.dp),
            label = stringResource(R.string.name),
            value = state.name,
            readOnly = true,
        )
        AppTextField(
            modifier = Modifier
                .width(240.dp)
                .padding(top = 16.dp),
            value = state.surname,
            label = stringResource(R.string.surname),
            imeAction = ImeAction.Next,
            readOnly = true,
        )
        AppTextField(
            modifier = Modifier
                .width(240.dp)
                .padding(top = 16.dp),
            label = stringResource(R.string.age),
            value = state.age,
            readOnly = true,
        )
    }
}