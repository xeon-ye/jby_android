package com.hzw.baselib.util;

import rx.Observable;
import rx.subjects.PublishSubject;
import rx.subjects.SerializedSubject;
import rx.subjects.Subject;

public class AwRxBus {

    private static AwRxBus rxBus;
    private final Subject<Object, Object> _bus = new SerializedSubject<>(PublishSubject.create());

    private AwRxBus() {
    }

    public static AwRxBus getInstance() {
        if (rxBus == null) {
            synchronized (AwRxBus.class) {
                if (rxBus == null) {
                    rxBus = new AwRxBus();
                }
            }
        }
        return rxBus;
    }


    public void send(Object o) {
        _bus.onNext(o);
    }

    public Observable<Object> toObserverable() {
        return _bus;
    }
}