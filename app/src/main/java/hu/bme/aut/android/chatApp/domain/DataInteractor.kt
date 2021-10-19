package hu.bme.aut.android.chatApp.domain


import hu.bme.aut.android.chatApp.Network.startDataStore
import javax.inject.Inject

class DataInteractor  @Inject constructor() {

    fun initData() : Boolean {
        return startDataStore()
    }
}