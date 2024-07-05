package com.arch.portdomain

import com.arch.portdomain.model.StateLayer
import io.reactivex.rxjava3.core.Observable


interface Interactor {
    fun startCase()

    fun stopCase()

    fun byDomain() : Observable<StateLayer>

}