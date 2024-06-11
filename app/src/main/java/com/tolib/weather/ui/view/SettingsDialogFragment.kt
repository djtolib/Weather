package com.tolib.weather.ui.view

import com.tolib.weather.ui.WeatherAppPreferences
import android.app.AlertDialog
import android.app.Dialog
import android.os.Bundle
import android.widget.ArrayAdapter
import android.widget.EditText
import android.widget.Spinner
import androidx.fragment.app.DialogFragment
import androidx.fragment.app.activityViewModels
import com.tolib.weather.R
import com.tolib.weather.ui.viewModel.WeatherFragmentViewModel

class SettingsDialogFragment : DialogFragment() {
    private val viewModel: WeatherFragmentViewModel by activityViewModels()
    private val prefs: WeatherAppPreferences by lazy { WeatherAppPreferences(requireContext()) }
    override fun onCreateDialog(savedInstanceState: Bundle?): Dialog {
        val view = requireActivity().layoutInflater.inflate(R.layout.settings, null)

        val unitsSpinner = view.findViewById<Spinner>(R.id.units)
        val cityEdt = view.findViewById<EditText>(R.id.cityEdt)
        setSpinnerItemByValue(unitsSpinner, prefs.readUnit() )
        cityEdt.setText(prefs.readCity())
        val alertDialogBuilder = AlertDialog.Builder(requireActivity())
        alertDialogBuilder.setView(view)
        alertDialogBuilder.setTitle("Settings")

        alertDialogBuilder.setPositiveButton("Save") { dialog, _ ->
            val unitVal = unitsSpinner.selectedItem.toString()
            val city = cityEdt.text.toString()
            prefs.writeCity(city)
            prefs.writeUnit(unitVal)
            viewModel.getWeather(city = city, unit = unitVal)
            dialog.dismiss()
        }
        alertDialogBuilder.setNegativeButton("Cancel") { dialog, _ ->
            dialog.dismiss()
        }

        return alertDialogBuilder.create()
    }
    private fun setSpinnerItemByValue(spinner: Spinner, value: String) {
        val adapter = spinner.adapter
        if (adapter is ArrayAdapter<*>) {
            for (i in 0 until adapter.count) {
                if (adapter.getItem(i).toString() == value) {
                    spinner.setSelection(i)
                    break
                }
            }
        }
    }

}
