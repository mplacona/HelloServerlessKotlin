package uk.co.placona.helloserverlesskotlin

import android.os.*
import android.support.v7.app.*
import android.widget.*
import io.reactivex.android.schedulers.*
import io.reactivex.disposables.*
import io.reactivex.schedulers.*
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    private var disposable: Disposable? = null

    private val openWhiskApiService by lazy {
        OpenWhiskApiService.create()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        btn_greet.setOnClickListener {
            if (edit_name.text.toString().isNotEmpty()) {
                beginEcho(Model.Request(edit_name.text.toString()))
                edit_name.text.clear()
            }
        }
    }

    private fun beginEcho(request: Model.Request) {
        disposable = openWhiskApiService.nameEchoer(request)
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(
                        { result -> txt_greeting.text = result.message },
                        { error -> Toast.makeText(this, error.message, Toast.LENGTH_SHORT).show() }
                )
    }

    override fun onPause() {
        super.onPause()
        disposable?.dispose()
    }

}
