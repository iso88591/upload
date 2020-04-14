package com.example.myapplication

import okhttp3.Interceptor
import okhttp3.Response
import okhttp3.internal.http.RealInterceptorChain

class MyInterceptor:Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {

        val realChain = chain as RealInterceptorChain
        realChain.exchange()
        val transmitter = realChain.transmitter()
        realChain.call()
        realChain.connection()
        realChain.request()

//        return realChain.proceed(realChain)
        return  chain.proceed(chain.request())
    }
}
