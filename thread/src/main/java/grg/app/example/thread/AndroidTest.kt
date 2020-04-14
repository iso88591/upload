package grg.app.example.thread

class AndroidTest :Thread() {


    val lopper:Lopper by lazy { Lopper() }



}


fun main() {

    val androidTest = AndroidTest()

    androidTest.lopper.task = Runnable {
        Thread.sleep(2000)
        println("手动方式发送到发送到")
        androidTest.lopper.quit()
    }

    androidTest.start()

}
