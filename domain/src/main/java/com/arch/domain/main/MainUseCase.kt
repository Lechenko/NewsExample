package com.arch.domain.main

import com.arch.domain.BaseInteractor
import com.arch.portdomain.main.IMainUseCase
import com.arch.portdomain.model.StateFlow
import io.reactivex.rxjava3.core.Observable
import javax.inject.Inject

class MainUseCase @Inject constructor() : BaseInteractor(),
    IMainUseCase {
    override fun startCase() {

    }

    override fun stopCase() {

    }

    override fun byDomain(): Observable<StateFlow> = observationState()

}