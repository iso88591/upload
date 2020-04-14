package grg.app.example.thread


class KtMain {

    var data: Int? = null

    val moniter1: Object = Object()

    fun read() {

        synchronized(moniter1) {
            while (data == null) {
                try {
                    moniter1.wait()
                } catch (e: Exception) {
                }
            }
            println("data=${data}")
        }


    }

    @Synchronized
    fun write(d: Int) {
        synchronized(moniter1) {
            data = d
            try {
                moniter1.notifyAll()
            } catch (e: Exception) {
            }
        }
    }


    fun doIt() {

        Thread(Runnable {
            println("读前睡眠")
            Thread.sleep(500)
            println("准备开始读了")
            read()
            println("读完了")
        }).start()

        Thread(Runnable {
            println("写前睡眠")
            Thread.sleep(3000)
            println("准备开始写了")
            write(10)
            println("写完了")
        }).start()

    }


    companion object {


        @JvmStatic
        fun main(args: Array<String>) {

            KtMain().doIt()

        }


    }


}


