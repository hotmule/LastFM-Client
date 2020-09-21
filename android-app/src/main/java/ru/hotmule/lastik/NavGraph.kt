package ru.hotmule.lastik

import android.compose.utils.Navigator
import android.os.Parcelable
import kotlinx.android.parcel.Parcelize

sealed class Destination : Parcelable {
    @Parcelize object Library : Destination()
    @Parcelize object Profile : Destination()
}

class Actions(navigator: Navigator<Destination>) {
    val toProfile: () -> Unit = { navigator.navigate(Destination.Profile) }
    val toBack: () -> Unit = { navigator.back() }
}