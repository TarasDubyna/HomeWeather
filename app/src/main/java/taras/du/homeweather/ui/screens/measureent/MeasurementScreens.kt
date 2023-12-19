package taras.du.homeweather.ui.screens.measureent

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.livedata.observeAsState
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview

@Preview
@Composable
fun MeasurementChartScreen(modifier: Modifier = Modifier) {
    Box(modifier = Modifier.fillMaxWidth()) {

    }
}

@Preview
@Composable
fun MeasurementTableScreen(measurementViewModel: MeasurementViewModel) {
    val data by measurementViewModel.data.observeAsState(emptyList())
    Box(modifier = Modifier.fillMaxSize()) {
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(data){

            }
        }
    }
}