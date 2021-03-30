package com.example.activityworkexample

import android.content.Intent
import android.os.Bundle
import android.widget.Button
import android.widget.TextView
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.appcompat.widget.Toolbar
import com.example.activityworkexample.model.CarInfoModel
import com.example.activityworkexample.model.DriveUnit

class InfoActivity : AppCompatActivity() {

    private lateinit var toolbar: Toolbar
    private lateinit var carNameText: TextView
    private lateinit var driveUnitText: TextView
    private lateinit var additionalTermsText: TextView
    private lateinit var acceptButton: Button

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_info)

        val carData = intent.getParcelableExtra<CarInfoModel>(CAR_INFO_EXTRA)

        initViews()
        fillViews(carData)
        initListeners()
    }

    private fun initViews() {
        carNameText = findViewById(R.id.text_car_name_value)
        driveUnitText = findViewById(R.id.text_drive_unit_value)
        additionalTermsText = findViewById(R.id.text_additional_terms_value)

        acceptButton = findViewById(R.id.button_confirm)

        toolbar = findViewById(R.id.toolbar)
        toolbar.inflateMenu(R.menu.close_menu)
    }

    private fun fillViews(carData: CarInfoModel?) {
        carData?.let {
            carNameText.text = getCarName(carData)
            driveUnitText.setText(getDriveUnitText(it.carDriveUnit))
            additionalTermsText.text = getAdditionalTerms(carData)
        }
    }

    private fun initListeners() {
        toolbar.setOnMenuItemClickListener {
            when (it.itemId) {
                R.id.close -> {
                    setResult(RESULT_CANCELED)
                    finish()
                    return@setOnMenuItemClickListener true
                }
            }
            false
        }

        acceptButton.setOnClickListener {
            setResult(RESULT_OK)
            finish()
        }
    }

    @StringRes
    private fun getDriveUnitText(driveUnit: DriveUnit): Int {
        return when (driveUnit) {
            DriveUnit.FRONT -> R.string.front_drive_radio
            DriveUnit.REAR -> R.string.rear_drive_radio
            DriveUnit.FOUR -> R.string.four_drive_radio
            DriveUnit.NONE -> R.string.no_data_placeholder
        }
    }

    private fun getAdditionalTerms(carData: CarInfoModel): String {
        val stringList = arrayListOf<String>()
        if (carData.needParkingLot) stringList.add(getString(R.string.parking_lot_check))
        if (carData.needWash) stringList.add(getString(R.string.car_wash_check))

        return if (stringList.size > 0) stringList.joinToString(", ")
        else "–"
    }

    private fun getCarName(carData: CarInfoModel): String =
        if (carData.carBrand.isEmpty() && carData.carModel.isEmpty()) "–"
        else {
            "${carData.carBrand} ${carData.carModel}"
        }

    companion object {
        const val CAR_INFO_EXTRA = "InfoActivity.CAR_INFO_EXTRA"
    }
}