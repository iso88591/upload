import com.android.build.gradle.BaseExtension
import com.android.build.gradle.internal.dsl.DefaultConfig
import grg.plugin.gradle.TestPluginConfig
import org.gradle.api.Plugin
import TransFormmTest
import org.gradle.api.Project


class TestPlugin implements Plugin<Project> {

    @Override
    void apply(Project project) {

//        def p = project.extensions.create("testPlugin", TestPluginConfig)

        //android声明的插件
        BaseExtension androidExtension = project.extensions.getByType(BaseExtension)
//       def map= project.extensions.properties.

//        DefaultConfig defaultConfig = androidExtension.getDefaultConfig()
//        defaultConfig.buildConfigField("String", "APP_SHARE_IP", "\"http://h0w.co/q/1Z\"")

//        project.afterEvaluate {
//
//            System.err.println(p.name)
//
//        }


        def trans = new TransFormmTest()
        androidExtension.registerTransform(trans)


    }


}
