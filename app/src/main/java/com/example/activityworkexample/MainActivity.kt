package com.example.activityworkexample

import android.content.Intent
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.CheckBox
import android.widget.RadioButton
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import com.example.activityworkexample.model.CarInfoModel
import com.example.activityworkexample.model.DriveUnit
import com.google.android.material.snackbar.Snackbar
import com.google.android.material.textfield.TextInputEditText

class MainActivity : AppCompatActivity() {

    private lateinit var rootView: View
    private lateinit var toolbar: Toolbar
    private lateinit var confirmButton: Button
    private lateinit var carBrandEditText: TextInputEditText
    private lateinit var carModelEditText: TextInputEditText
    private lateinit var frontDriveRadio: RadioButton
    private lateinit var rearDriveRadio: RadioButton
    private lateinit var fourDriveRadio: RadioButton
    private lateinit var parkingLotCheck: CheckBox
    private lateinit var carWashCheck: CheckBox

    private lateinit var snackbar: Snackbar

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViews()
        initListeners()
    }

    private fun initViews() {
        rootView = findViewById(R.id.rootView)
        toolbar = findViewById(R.id.toolbar)
        confirmButton = findViewById(R.id.button_confirm)
        carBrandEditText = findViewById(R.id.text_edit_car_brand)
        carModelEditText = findViewById(R.id.text_edit_car_model)
        frontDriveRadio = findViewById(R.id.front_wheel_radio_button)
        rearDriveRadio = findViewById(R.id.rear_wheel_radio_button)
        fourDriveRadio = findViewById(R.id.four_wheel_radio_button)
        parkingLotCheck = findViewById(R.id.parking_lot_checkbox)
        carWashCheck = findViewById(R.id.car_wash_checkbox)
    }

    private fun initListeners() {
        confirmButton.setOnClickListener {
            openInfoActivity()
        }
    }

    private fun collectUiData() = CarInfoModel(
        carBrandEditText.text.toString(),
        carModelEditText.text.toString(),
        getDriveUnit(),
        parkingLotCheck.isChecked,
        carWashCheck.isChecked
    )

    private fun getDriveUnit(): DriveUnit = when {
        frontDriveRadio.isChecked -> {
            DriveUnit.FRONT
        }
        rearDriveRadio.isChecked -> {
            DriveUnit.REAR
        }
        fourDriveRadio.isChecked -> {
            DriveUnit.FOUR
        }
        else -> {
            DriveUnit.NONE
        }
    }

    private fun openInfoActivity() {
        val starter = Intent(this, InfoActivity::class.java)
        starter.putExtra(InfoActivity.CAR_INFO_EXTRA, collectUiData())
        startActivityForResult(starter, DATA_REQUEST_CODE)
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)

        if (requestCode == DATA_REQUEST_CODE && resultCode == RESULT_OK) {
            showSnackBar(R.string.all_ok_snackbar)
        }

        if (requestCode == DATA_REQUEST_CODE && resultCode == RESULT_CANCELED) {
            showSnackBar(R.string.screen_was_closed)
        }
    }

    private fun showSnackBar(@StringRes string: Int) {
        snackbar =
            Snackbar.make(rootView, this.getText(string), Snackbar.LENGTH_LONG)
                .setAction(android.R.string.ok, View.OnClickListener {
                    snackbar.dismiss()
                })
        snackbar.show()
    }

    companion object {
        private const val DATA_REQUEST_CODE = 0
    }
}