package taras.du.homeweather.ui.screens.measureent

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import dagger.hilt.android.scopes.ViewModelScoped
import taras.du.domain.model.MeasurementValue

@ViewModelScoped
class MeasurementViewModel: ViewModel() {
    val data: LiveData<List<MeasurementValue>> = MutableLiveData(emptyList())
}