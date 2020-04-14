package grg.app.example.thread

class Lopper {


    var task:Runnable?=null

    private var quit = false



    fun quit(){
        quit = true
    }


    fun lopper() {

        while (!quit){
            synchronized(this){

                task?.run()
                task = null

            }


        }


    }

}
