package com.example.easyfinancing.ui.models.extract

import android.os.Parcel
import android.os.Parcelable
import java.time.LocalDate

data class Movimentation(
    val id : Int,
    val date : LocalDate,
    val type : Boolean,
    val mainDescription : String,
    val categoryId : Int,
    val movAmount : String,
    val cardId : Int,
    val cardInstalments : Int,
    val recurenceId : Int,
    val budgetId : Int
) : Parcelable {
    constructor(parcel: Parcel) : this(
        parcel.readInt(),
        LocalDate.parse(parcel.readString()),
        parcel.readByte() != 0.toByte(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readString() ?: "",
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt(),
        parcel.readInt()
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeInt(id)
        parcel.writeString(date.toString())
        parcel.writeByte(if (type) 1 else 0)
        parcel.writeString(mainDescription)
        parcel.writeInt(categoryId)
        parcel.writeString(movAmount)
        parcel.writeInt(cardId)
        parcel.writeInt(cardInstalments)
        parcel.writeInt(recurenceId)
        parcel.writeInt(budgetId)
    }

    override fun describeContents(): Int {
        return 0
    }

    companion object CREATOR : Parcelable.Creator<Movimentation> {
        override fun createFromParcel(parcel: Parcel): Movimentation {
            return Movimentation(parcel)
        }

        override fun newArray(size: Int): Array<Movimentation?> {
            return arrayOfNulls(size)
        }
    }
}