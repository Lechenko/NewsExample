package com.arch.presentation.base

import com.arch.portdomain.model.StateLayer
import io.reactivex.rxjava3.core.Observable

interface IState{
    fun state(): Observable<StateLayer>
}