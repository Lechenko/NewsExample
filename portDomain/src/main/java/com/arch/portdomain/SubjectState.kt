//package com.arch.portdomain
//
//import com.arch.portdomain.model.EnumStateFlow
//import com.arch.portdomain.model.StateFlow
//import io.reactivex.rxjava3.android.schedulers.AndroidSchedulers
//import io.reactivex.rxjava3.core.Observable
//import io.reactivex.rxjava3.disposables.Disposable
//import io.reactivex.rxjava3.subjects.PublishSubject
//import javax.inject.Inject
//
//interface StateFlowListener{
//    fun onNext(state : StateFlow)
//    fun onError(state : StateFlow)
//    fun observationState() : Observable<StateFlow>
//    fun reset()
//}
//class SubjectState  @Inject constructor() : StateFlowListener {
//    private val publish : PublishSubject<StateFlow>  = PublishSubject.create()
//    override fun onNext(state : StateFlow) = publish.onNext(state)
//
//    override fun onError(state : StateFlow) = state.message.let {publish.onError(Throwable(it))}
//
//    override fun observationState(): Observable<StateFlow> =
//         Observable.defer{publish}
//            .filter{it.status != 0}
//
//
//    override fun reset() = publish.onNext(StateFlow(EnumStateFlow.RESET.const))
//}