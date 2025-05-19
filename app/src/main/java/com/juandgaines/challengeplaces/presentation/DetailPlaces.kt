package com.juandgaines.challengeplaces.presentation

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Scaffold
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.remember
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.semantics.contentDescription
import androidx.compose.ui.semantics.semantics
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.model.LatLng
import com.google.maps.android.compose.GoogleMap
import com.google.maps.android.compose.Marker
import com.google.maps.android.compose.rememberCameraPositionState
import com.google.maps.android.compose.rememberUpdatedMarkerState
import com.juandgaines.challengeplaces.R

@Composable
fun DetailPlaces(
    state: SearchState
) {
    val mapScreenDescription = stringResource(R.string.map_screen_description)
    val mapScreenMarkerDescription = stringResource(R.string.map_screen_marker_description)

    Scaffold { padding ->
        val cameraPositionState = rememberCameraPositionState()

        val markerPosition = remember(state.currentSelectedCity) {

            state.currentSelectedCity?.let {
                LatLng(
                    (state.currentSelectedCity.lat.toFloat()).toDouble(),
                    (state.currentSelectedCity.lon.toFloat()).toDouble()
                )
            }
        }
        val marker = markerPosition?.let {
            rememberUpdatedMarkerState(it)
        }

        LaunchedEffect(state.currentSelectedCity) {
            if (state.currentSelectedCity != null) {
                val latLng = LatLng(
                    state.currentSelectedCity.lat,
                    state.currentSelectedCity.lon
                )
                cameraPositionState.animate(
                    CameraUpdateFactory.newLatLngZoom(latLng, 17f)
                )
            }
        }

        GoogleMap(
            cameraPositionState = cameraPositionState,
            modifier = Modifier
                .fillMaxSize()
                .semantics {
                    contentDescription = mapScreenDescription
                }
                .padding(padding)
        ) {
            marker?.let {
                Marker(
                    state = it,
                   contentDescription = mapScreenMarkerDescription
                )
            }

        }
    }
}