package hu.bme.aut.android.chatApp.ui.map

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import hu.bme.aut.android.chatApp.ChatApplication.Companion.currentConversation
import hu.bme.aut.android.chatApp.R
import kotlinx.android.synthetic.main.fragment_map.*


class MapFragment: Fragment() {
    private var location: String = ""

    private val callback = OnMapReadyCallback { googleMap ->

        val myMarker = LatLng(0.0, 0.0)
        googleMap.addMarker(MarkerOptions().position(myMarker).title("Actual location"))
        googleMap.moveCamera(CameraUpdateFactory.newLatLng(myMarker))

        googleMap.setOnMapClickListener() {
            googleMap.clear()
            googleMap.addMarker(MarkerOptions().position(it).title("Actual location"))
            googleMap.moveCamera(CameraUpdateFactory.newLatLng(it))
            location = it.toString()

        }

    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        return inflater.inflate(R.layout.fragment_map, container, false)
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        val mapFragment = childFragmentManager.findFragmentById(R.id.map) as SupportMapFragment?
        mapFragment?.getMapAsync(callback)

        selectLocationButton.setOnClickListener{
            val action = currentConversation?.let { it1 -> MapFragmentDirections.actionMapFragment2ToChatFragment(it1.id, location) }
            if (action != null) {
                findNavController().navigate(action)
            }
        }
    }
}
