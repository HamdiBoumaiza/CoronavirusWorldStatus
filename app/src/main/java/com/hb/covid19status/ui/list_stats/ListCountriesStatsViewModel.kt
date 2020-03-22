package com.hb.covid19status.ui.list_stats

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hb.covid19status.data.ResultData
import com.hb.covid19status.model.CountryStat
import com.hb.covid19status.repository.AppRepositoryImpl
import kotlinx.coroutines.launch
import javax.inject.Inject

class ListCountriesStatsViewModel @Inject constructor(
    private val repositoryImpl: AppRepositoryImpl) : ViewModel() {

    private var _resultListStats = MutableLiveData<List<CountryStat>>()
    var resultListStats: LiveData<List<CountryStat>> = _resultListStats

    private var _errorMessage = MutableLiveData<String>()
    var errorMessage: LiveData<String> = _errorMessage

    private var _showLoading = MutableLiveData<Boolean>()
    var showLoading: LiveData<Boolean> = _showLoading

    fun getListOfStats() {
        _showLoading.postValue(true)
        viewModelScope.launch {
            try {
                when (val response = repositoryImpl.listCountriesWithStats()) {
                    is ResultData.Success -> {
                        _showLoading.postValue(false)
                        _resultListStats.postValue(response.data)
                    }
                    is ResultData.Error -> {
                        _showLoading.postValue(false)
                        _errorMessage.postValue("")
                    }
                }
            } catch (e: Exception) {
                _showLoading.postValue(false)
                _errorMessage.postValue(e.message)
            }
        }
    }
}