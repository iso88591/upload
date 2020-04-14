import com.android.build.gradle.AppExtension
import com.android.build.gradle.BaseExtension
import okhttp3.Call
import okhttp3.Callback
import okhttp3.MediaType
import okhttp3.MultipartBody
import okhttp3.OkHttpClient
import okhttp3.Request
import okhttp3.RequestBody
import okhttp3.Response
import okio.BufferedSink
import org.gradle.api.Plugin
import org.gradle.api.Project
import UploadConfig
import org.gradle.api.Task
import org.jetbrains.annotations.NotNull

class UploadPlugin implements Plugin<Project> {


    @Override
    void apply(Project project) {

        UploadConfig config = project.extensions.create("autoupload", UploadConfig)

       if ("".equals(config.pgyKey.trim())){
           System.err.println("请在app 的build.gradle中配置autoupload的pgyKey")
           return
       }

        project.task("uploadAfterAssembleDebug", {

            dependsOn("assembleDebug").configure {

                doLast {
                    println "===============uploadAfterAssembleDebug"

                    /**
                     * applicationVariants.all { variant ->
                     *         if (variant.buildType.name.equals('release')) {*             variant.outputs.all { output ->
                     *                 def buildName = "Downloader"
                     *                 def type = variant.buildType.name
                     *                 def releaseApkName = buildName + '_' + type + "_" + versionName + '_' + getTime() + '.apk'
                     *                 outputFileName = releaseApkName
                     *                 variant.packageApplication.outputDirectory = new File("./apk")
                     *}*}*}*/

                    //app/build/outputs/apk/debug/app-debug.apk
                    AppExtension android = project.extensions.getByType(AppExtension)
//                    android.applicationVariants.each {
//                    }

                    ///Users/grg/AndroidStudioProjects/MyApplication/app/build/outputs/apk/debug/app-debug.apk
                    //获取包的路径的方法
                    File file = android.applicationVariants.first().outputs.first().outputFile
                    println "文件路径====" + file.absolutePath
                    if (file.exists()) {

                        Scanner scanner = new Scanner(System.in)

                        println "请设置下载安装密码(回车键结束,默认1234):"
                        String installPassword = scanner.nextLine()
                        installPassword = "".equals(installPassword)?"1234":installPassword

                        println "请添加此版本描述(回车键结束):"
                        String des = scanner.nextLine()

                        println "===========准备发包==========="
                        println "===========安装密码:"+installPassword
                        println "===========版本描述:"+des

                        RequestBody fileBody = RequestBody.create(MediaType.parse("multipart/form-data"), file)

                        RequestBody requestBody = new MultipartBody.Builder()
                                .setType(MultipartBody.FORM)

                        //key 后面换成从配置中获取
                                .addFormDataPart("_api_key", config.pgyKey)

                        //文件
                                .addFormDataPart("file", file.name, fileBody)

                        //	(必填)应用安装方式，值为(2,3)。2：密码安装，3：邀请安装
                                .addFormDataPart("buildInstallType", "2")

                        //(必填) 设置App安装密码
                                .addFormDataPart("buildPassword", installPassword)

                        //选填) 版本更新描述，请传空字符串，或不传。
                                .addFormDataPart("buildUpdateDescription", des)
                                .build()

                        Request request = new Request.Builder()
                                .url("https://www.pgyer.com/apiv2/app/upload")
                                .post(requestBody)
                                .build()

                        new OkHttpClient().newCall(request).enqueue(new Callback() {
                            @Override
                            void onFailure(@NotNull Call call, @NotNull IOException e) {
                                println "=================onFailure"
                            }

                            @Override
                            void onResponse(@NotNull Call call, @NotNull Response response) throws IOException {
                                println "=================onResponse"
                            }
                        })
                    } else {
                        println "文件不存在"
                    }


                }
            }


        })


    }
}
