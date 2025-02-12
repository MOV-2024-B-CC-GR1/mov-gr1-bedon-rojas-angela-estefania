package com.example.deber01

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.google.android.gms.maps.CameraUpdateFactory
import com.google.android.gms.maps.GoogleMap
import com.google.android.gms.maps.OnMapReadyCallback
import com.google.android.gms.maps.SupportMapFragment
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.MarkerOptions

class MapaActivity : AppCompatActivity(), OnMapReadyCallback {

    private lateinit var mMap: GoogleMap
    private lateinit var db: BaseDatosSQLite
    private var duenoId: Int = -1

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_mapa)

        // Recibir ID del dueño
        duenoId = intent.getIntExtra("duenoId", -1)
        db = BaseDatosSQLite(this)

        // Inicializa el fragmento del mapa
        val mapFragment = supportFragmentManager
            .findFragmentById(R.id.mapFragment) as SupportMapFragment
        mapFragment.getMapAsync(this)
    }

    override fun onMapReady(googleMap: GoogleMap) {
        mMap = googleMap

        // Habilitar controles de zoom y gestos
        mMap.uiSettings.isZoomControlsEnabled = true
        mMap.uiSettings.isZoomGesturesEnabled = true
        mMap.uiSettings.isScrollGesturesEnabled = true

        // Opcional: Configurar tipo de mapa (NORMAL, SATELLITE, TERRAIN, HYBRID)
        mMap.mapType = GoogleMap.MAP_TYPE_NORMAL

        val dueno = db.obtenerDueñoPorId(duenoId)

        if (dueno != null) {
            val latLng = LatLng(dueno.lat, dueno.lng)
            // Mover la cámara al Dueño (zoom 15 por ejemplo)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
            // Agregar un marcador
            mMap.addMarker(MarkerOptions().position(latLng).title("Ubicación de ${dueno.nombre}"))
        } else {
            // Si no tiene lat/lng o no existe
            // Ejemplo: Mostrar Quito de referencia
            val quito = LatLng(-0.180653, -78.467834)
            mMap.moveCamera(CameraUpdateFactory.newLatLngZoom(quito, 6f))
        }
    }
}
