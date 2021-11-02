package hu.bme.aut.android.chatApp.ui.map

import android.location.Location
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.google.android.gms.maps.*
import com.google.android.gms.maps.model.LatLng
import com.google.android.gms.maps.model.Marker
import com.google.android.gms.maps.model.MarkerOptions
import hu.bme.aut.android.chatApp.ChatApplication.Companion.currentConversation
import hu.bme.aut.android.chatApp.R
import hu.bme.aut.android.chatApp.ui.Chat.ChatFragmentArgs
import kotlinx.android.synthetic.main.fragment_map.*
import okio.utf8Size


class MapFragment: Fragment() {
    private val args: ChatFragmentArgs by navArgs()
    private var location: String = ""

    private val callback = OnMapReadyCallback { googleMap ->

        if(args.location != "0") location = args.location
        val myMarker = stringToLocation(location)
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

    private fun stringToLocation(locationString: String): LatLng {
        val lat = locationString.subSequence(10, 27).toString().fullTrim().toDouble()
        var longx = 0
        var longy = 0
        if(locationString[28] == ','){
            longx = 29
            longy = 43
        }
        else if(locationString[28] == ','){
            longx = 30
            longy = 44
        }
        val long = locationString.subSequence(longx, longy).toString().fullTrim().toDouble()
        return LatLng(lat, long)
    }

    private fun String.fullTrim() = trim().replace("\uFEFF", "")
}
