package com.example.apptest.domain.model

import android.os.Parcelable
import androidx.room.Entity
import androidx.room.PrimaryKey
import com.google.gson.annotations.SerializedName
import kotlinx.parcelize.Parcelize

@Parcelize
@Entity
data class User(
    @PrimaryKey
    val id: Long,
    @SerializedName("first_name") // Map to JSON field "first_name"
    val firstName: String,
    @SerializedName("last_name") // Map to JSON field "last_name"
    val lastName: String,
    val age: Int,
    val male: Boolean,
    val address: String,
    val birthday: Long
) : Parcelable


@Parcelize
data class UserRequest(
    @SerializedName("first_name")
    val firstName: String,
    @SerializedName("last_name")
    val lastName: String,
    val age: Int,
    val male: Boolean,
    val address: String,
    val birthday: Long
) : Parcelable


fun User.toUserRequest(): UserRequest {
    return UserRequest(
        firstName = this.firstName,
        lastName = this.lastName,
        age = this.age,
        male = this.male,
        address = this.address,
        birthday = this.birthday
    )
}