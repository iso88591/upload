package com.example.myapplication

import okhttp3.*

class LogIntercepter : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {


        var request: Request = chain.request()
        //打印请求参数

        request = request.newBuilder().addHeader("user-agent",
            "Mozilla/5.0 (Macintosh; Intel Mac OS X 10_15_3) AppleWebKit/537.36 (KHTML, like Gecko) Chrome/80.0.3987.149 Safari/537.36").build()
        val headers: Headers = request.headers()

        val stringBuffer: StringBuffer = StringBuffer()
        val url = request.url()
        stringBuffer.append(
            """
==============================================================================
|${request.method()} ${url.url().path} ${url.url().protocol}
|${url.host()}
|
"""
        )

        if ("GET".equals(request.method())) {
            for (i in 1..url.querySize()) {
                val queryParameterName = url.queryParameterName(i - 1)
                val queryParameterValue = url.queryParameterValue(i - 1)
                stringBuffer.append(
                    """
|${queryParameterName}:${queryParameterValue}
        """
                )
            }
        }

        //headers
        for (i in 1..headers.size()) {
            val name = headers.name(i - 1)
            stringBuffer.append(
                """
|$name:${headers.get(name)}
        """
            )
        }

        //end line
        stringBuffer.append(
            """
==============================================================================                
            """
        )

        //print request
        println(stringBuffer)

        //
        val response = chain.proceed(request)
        //

        stringBuffer.setLength(0)
        //print response

        //状态行
        stringBuffer.append(
 """
==============================================================================    
|${response.protocol().name} ${response.code()} ${response.message()}
 """)

        //headers
        val resHeaders = response.headers()
        for (i in 1..resHeaders.size()) {
            val name = resHeaders.name(i - 1)
            stringBuffer.append("""
|$name:${headers.get(name)}
"""
            )
        }

        val clone = response.newBuilder().build()
        var body = clone.body()

        val mediaType: MediaType? = body?.contentType()
        if (mediaType != null) {
            val resp = body?.string()
            body = ResponseBody.create(mediaType, resp)

            stringBuffer.append(resp)
        }


        //end line
        stringBuffer.append(
            """
==============================================================================                
            """
        )

        print(stringBuffer)


        return response.newBuilder().body(body).build()

    }
}
