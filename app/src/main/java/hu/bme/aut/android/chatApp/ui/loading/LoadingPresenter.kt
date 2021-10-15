package hu.bme.aut.android.chatApp.ui.loading

import co.zsmb.rainbowcake.withIOContext
import hu.bme.aut.android.chatApp.domain.DataInteractor
import javax.inject.Inject

class LoadingPresenter @Inject constructor(
    private val dataInteractor: DataInteractor,
){
    suspend fun initData() : Boolean = withIOContext{
        dataInteractor.initData()
    }
}