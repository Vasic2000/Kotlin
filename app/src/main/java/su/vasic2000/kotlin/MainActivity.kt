package su.vasic2000.kotlin

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import kotlinx.android.synthetic.main.activity_main.*

class MainActivity : AppCompatActivity() {

    lateinit var viewModel: MainViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        viewModel = ViewModelProviders.of(this).get(MainViewModel::class.java)
        val livedata =  viewModel.getViewStateLiveData()
        val emodzy = viewModel.getEmodzyLibeData()

        livedata.observe(this, Observer {
            Toast.makeText(this, it, Toast.LENGTH_SHORT).show()
        })

        livedata.observe(this, Observer {
            tv_hello.text = it
        })

        emodzy.observe(this, Observer {
            tv_counter.text = it
        })

        btnPlus.setOnClickListener {
            viewModel.updateHelloPlus()
        }

        btnMinus.setOnClickListener {
            viewModel.updateHelloMinus()
        }

        btnExit.setOnClickListener {
            finish()
        }
    }
}
