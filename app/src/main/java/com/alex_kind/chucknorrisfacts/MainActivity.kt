package com.alex_kind.chucknorrisfacts

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import com.alex_kind.chucknorrisfacts.databinding.ActivityMainBinding
import kotlinx.coroutines.*
import retrofit2.awaitResponse


class MainActivity : AppCompatActivity() {

    lateinit var bind: ActivityMainBinding
    var retrofitBuilder = RetrofitCreator().getRetrofit()
    var category = ""

    private val coroutineContext = SupervisorJob() + Dispatchers.Main.immediate
    private val coroutineScope: CoroutineScope = CoroutineScope(coroutineContext)

    var counter = 0


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        bind = ActivityMainBinding.inflate(layoutInflater)
        setContentView(bind.root)

        resp()

        buttonsListenerCoroutines()
    }

    private fun buttonsListenerCoroutines() = with(bind) {

        buttonCategory.setOnClickListener {

            coroutineScope.launch {
                if (counter == 15) {
                    counter = 0
                } else {
                    counter++
                }
                resp()

            }
        }

        buttonValue.setOnClickListener {
            coroutineScope.launch {
                delay(500)
                respTwo()

            }
        }
    }

    private fun resp() {
        coroutineScope.launch {
            val response = retrofitBuilder.categories().awaitResponse()
            if (response.isSuccessful) {
                val body = response.body()!!

                if (category == "") {
                    category = body[counter]
                    respTwo()
                }

                category = body[counter]
                bind.textCategory.text = body[counter]

            }
        }
    }

    private fun respTwo() {

        coroutineScope.launch {
            val responseTWo = retrofitBuilder.random(category).awaitResponse()
            if (responseTWo.isSuccessful) {
                val body = responseTWo.body()!!

                if (body.value == bind.textValue.text) {  //to not have equal values
                    respTwo()
                }

                bind.textValue.text = body.value
            }

        }
    }
}