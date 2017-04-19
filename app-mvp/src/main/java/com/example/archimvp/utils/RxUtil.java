package com.example.archimvp.utils;

import com.example.archimvp.model.HttpResponse;
import com.example.archimvp.model.exception.APIException;

import rx.Observable;
import rx.Subscriber;
import rx.android.schedulers.AndroidSchedulers;
import rx.functions.Func1;
import rx.schedulers.Schedulers;

/**
 * Created by LeoPoldCrossing on 2017/3/15.
 */

public class RxUtil {

    /**
     * 统一线程处理
     * */
    public static <T>Observable.Transformer<T,T> rxSchedulerHelper(){
        return new Observable.Transformer<T, T>() {
            @Override
            public Observable<T> call(Observable<T> tObservable) {
                return tObservable.subscribeOn(Schedulers.io())
                        .observeOn(AndroidSchedulers.mainThread());
            }
        };
    }

//    /**
//     * 统一返回结果处理
//     * */
//    public static <T> Observable.Transformer<HttpResponse<T>,T> handleResult(){
//        return new Observable.Transformer<HttpResponse<T>, T>(){
//
//            @Override
//            public Observable<T> call(Observable<HttpResponse<T>> httpResponseObservable) {
//                return httpResponseObservable.compose(RxUtil.<HttpResponse<T>>rxSchedulerHelper())
//                        .flatMap(new Func1<HttpResponse<T>, Observable<T>>() {
//                            @Override
//                            public Observable<T> call(HttpResponse<T> tHttpResponse) {
//                                if(tHttpResponse.getMessage().getCode() != 0) {
//                                    return Observable.error(new APIException(tHttpResponse.getMessage().getMessage()));
//                                }else{
//                                    return (Observable<T>) createData(tHttpResponse);
//                                }
//                            }
//                });
//            }
//        };
//    }

    public static <T> Observable.Transformer<HttpResponse<T>, T> handleResult() {   //compose判断结果
        return new Observable.Transformer<HttpResponse<T>, T>() {
            @Override
            public Observable<T> call(Observable<HttpResponse<T>> httpResponseObservable) {
                return httpResponseObservable.flatMap(new Func1<HttpResponse<T>, Observable<T>>() {
                    @Override
                    public Observable<T> call(HttpResponse<T> tWXHttpResponse) {
                        if(tWXHttpResponse.getCode() == 200) {
                            return createData(tWXHttpResponse.getNewslist());
                        } else {
                            return Observable.error(new APIException("服务器返回error"));
                        }
                    }
                });
            }
        };
    }

    /**
     * 生成Observable
     * @param <T>
     * @return
     */
    public static <T> Observable<T> createData(final T t) {
        return Observable.create(new Observable.OnSubscribe<T>() {
            @Override
            public void call(Subscriber<? super T> subscriber) {
                try {
                    subscriber.onNext(t);
                    subscriber.onCompleted();
                } catch (Exception e) {
                    subscriber.onError(e);
                }
            }
        });
    }

}
