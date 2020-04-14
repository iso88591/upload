package com.example.myapplication


import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import okhttp3.OkHttpClient


class MainActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

//        newsView.setImageResource(R.drawable.test)

//        TypedValue.applyDimension(TypedValue.COMPLEX_UNIT_DIP,15f,Resources.getSystem().displayMetrics)

        val okHttpClient: OkHttpClient = OkHttpClient
            .Builder()
//            .authenticator { route: Route?, response: Response ->
//            }
            .addInterceptor(LogIntercepter())
            .build()

//
//        val retrofit: Retrofit = Retrofit
//            .Builder()
//            .baseUrl("https://www.baidu.com/")
//            .addConverterFactory(object : Converter.Factory() {
//                override fun responseBodyConverter(
//                    type: Type,
//                    annotations: Array<Annotation>,
//                    retrofit: Retrofit
//                ): Converter<ResponseBody, String>? {
//                    return Converter<ResponseBody, String> {
//                        it.string()
//                    }
//                }
//            })
//            .client(okHttpClient)
//            .build()
//
//        val api = retrofit.create(Api::class.java)
//        api.getString("地方").enqueue(object : Callback<String> {
//            override fun onFailure(call: Call<String>, t: Throwable) {
//                t.printStackTrace()
//            }
//
//            override fun onResponse(call: Call<String>, response: retrofit2.Response<String>) {
////                println("response = ${response.body()}")
//            }
//
//        })

    }


}
