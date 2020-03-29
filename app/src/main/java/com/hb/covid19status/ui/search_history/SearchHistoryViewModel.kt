package com.hb.covid19status.ui.search_history

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.hb.covid19status.data.ResponseHistoryCountry
import com.hb.covid19status.data.ResultData
import com.hb.covid19status.repository.AppRepositoryImpl
import kotlinx.coroutines.launch
import javax.inject.Inject

class SearchHistoryViewModel @Inject constructor(
    private val repositoryImpl: AppRepositoryImpl
) : ViewModel() {

    private var _resultHistory = MutableLiveData<ResponseHistoryCountry>()
    var resultHistory: LiveData<ResponseHistoryCountry> = _resultHistory

    private var _resulAffectedCountries = MutableLiveData<List<String>>()
    var resulAffectedCountries: LiveData<List<String>> = _resulAffectedCountries

    private var _errorMessage = MutableLiveData<String>()
    var errorMessage: LiveData<String> = _errorMessage

    private var _showLoading = MutableLiveData<Boolean>()
    var showLoading: LiveData<Boolean> = _showLoading

    fun getHistoryByDateByCountryStats(country: String, date: String) {
        _showLoading.postValue(true)
        viewModelScope.launch {
            try {
                when (val response = repositoryImpl.historyByDateByCountryStats(country, date)) {
                    is ResultData.Success -> {
                        _showLoading.postValue(false)
                        _resultHistory.postValue(response.data)
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

    fun getListAffectedCountries() {
        _showLoading.postValue(true)
        viewModelScope.launch {
            try {
                when (val response = repositoryImpl.affectedCountries()) {
                    is ResultData.Success -> {
                        _showLoading.postValue(false)
                        _resulAffectedCountries.postValue(response.data.affected_countries)
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