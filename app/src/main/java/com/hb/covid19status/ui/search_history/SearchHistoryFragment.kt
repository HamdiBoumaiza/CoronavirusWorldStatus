package com.hb.covid19status.ui.search_history

import android.app.DatePickerDialog
import android.app.DatePickerDialog.OnDateSetListener
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.annotation.Nullable
import androidx.databinding.DataBindingUtil
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProvider
import com.hb.covid19status.MainApplication
import com.hb.covid19status.R
import com.hb.covid19status.data.ResponseHistoryCountry
import com.hb.covid19status.databinding.FragmentHistoryStatsBinding
import com.hb.covid19status.ui.details_stats.DetailsCountriesStatsActivity
import com.hb.covid19status.utils.*
import kotlinx.android.synthetic.main.fragment_history_stats.*
import java.text.SimpleDateFormat
import java.util.*
import javax.inject.Inject

class SearchHistoryFragment : Fragment(), View.OnClickListener {

    private val appComponents by lazy { MainApplication.appComponents }

    private lateinit var mDatePickerDialog: DatePickerDialog
    @Inject
    lateinit var viewModelFactory: ViewModelProvider.Factory

    private fun getViewModel(): SearchHistoryViewModel {
        return viewModelProvider(viewModelFactory)
    }

    private lateinit var binding: FragmentHistoryStatsBinding

    override fun onCreateView(
        inflater: LayoutInflater,
        @Nullable container: ViewGroup?,
        @Nullable savedInstanceState: Bundle?
    ): View? {
        appComponents.inject(this)
        binding =
            DataBindingUtil.inflate(inflater, R.layout.fragment_history_stats, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        initObservers()
        initCalendar()
        binding.edittextCalendar.setOnClickListener(this)
        binding.btnSearch.setOnClickListener(this)
    }


    private fun initCalendar() {
        val myFormat = getString(R.string.format_date)
        val mDateCalendar = Calendar.getInstance()
        val currentDate = SimpleDateFormat(myFormat).format(Date())
        binding.edittextCalendar.setText(currentDate)
        val mDatePickerDialogListener =
            OnDateSetListener { _, year, month, dayOfMonth ->
                mDateCalendar.set(Calendar.YEAR, year)
                mDateCalendar.set(Calendar.MONTH, month)
                mDateCalendar.set(Calendar.DAY_OF_MONTH, dayOfMonth)
                val simpleDateFormat = SimpleDateFormat(myFormat, Locale.getDefault())
                binding.edittextCalendar.setText(simpleDateFormat.format(mDateCalendar.time))
            }

        mDatePickerDialog = DatePickerDialog(
            this.context!!, R.style.DatePickerTheme, mDatePickerDialogListener, mDateCalendar.get(
                Calendar.YEAR
            ),
            mDateCalendar.get(Calendar.MONTH), mDateCalendar.get(Calendar.DAY_OF_MONTH)
        )
        mDatePickerDialog.datePicker.maxDate = mDateCalendar.timeInMillis
        mDatePickerDialog.setCancelable(true)
    }

    override fun onClick(v: View?) {
        when (v) {
            edittext_calendar -> {
                if (::mDatePickerDialog.isInitialized) mDatePickerDialog.show()
            }
            btn_search -> {
                if (binding.edittextSearch.text.toString().isEmpty()) {
                    activity?.toast(getString(R.string.message_required_fields))
                } else {
                    getViewModel().getHistoryByDateByCountryStats(
                        binding.edittextSearch.text.toString(),
                        binding.edittextCalendar.text.toString()
                    )
                }
            }
        }
    }

    private fun initObservers() {
        getViewModel().resultHistory.observe(this, Observer { response ->
            response?.let { sendData(it) }
        })

        getViewModel().errorMessage.observe(this, Observer {
            handleError()
        })

        getViewModel().showLoading.observe(this, Observer { showLoading ->
            if (showLoading) binding.progress.show()
            else binding.progress.hide()
        })
    }

    private fun handleError() {
        activity?.toast(getString(R.string.error_message))
    }

    private fun sendData(responseHistoryCountryDate: ResponseHistoryCountry) {
        if (responseHistoryCountryDate.stat_by_country.isNotEmpty()) {
            Intent(activity, DetailsCountriesStatsActivity::class.java).apply {
                putExtra(HISTORY_EXTRA, responseHistoryCountryDate)
                startActivity(this)
            }
        } else {
            activity?.toast(getString(R.string.no_data_message))
        }
    }

}
