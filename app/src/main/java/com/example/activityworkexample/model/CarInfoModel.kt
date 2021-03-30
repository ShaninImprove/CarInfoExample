package com.example.activityworkexample.model

import android.os.Parcel
import android.os.Parcelable


class CarInfoModel(
    val carBrand: String,
    val carModel: String,
    val carDriveUnit: DriveUnit,
    val needParkingLot: Boolean,
    val needWash: Boolean
) : Parcelable {

    constructor(parcel: Parcel) : this(
        parcel.readString()!!,
        parcel.readString()!!,
        DriveUnit.values()[parcel.readInt()],
        parcel.readByte() != 0.toByte(),
        parcel.readByte() != 0.toByte()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeString(carBrand)
        parcel.writeString(carModel)
        parcel.writeInt(carDriveUnit.ordinal)
        parcel.writeByte(if (needParkingLot) 1 else 0)
        parcel.writeByte(if (needWash) 1 else 0)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<CarInfoModel> {
        override fun createFromParcel(parcel: Parcel): CarInfoModel {
            return CarInfoModel(parcel)
        }

        override fun newArray(size: Int): Array<CarInfoModel?> {
            return arrayOfNulls(size)
        }
    }
}
