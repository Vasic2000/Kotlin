package su.vasic2000.kotlin.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.firebase.ui.auth.AuthUI
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.consumeEach
import su.vasic2000.kotlin.R
import su.vasic2000.kotlin.data.errors.NoAuthException
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity<S> : AppCompatActivity(), CoroutineScope {

    val job = Job()
    private lateinit var dataJob: Job
    private lateinit var errorJob: Job

    override val coroutineContext: CoroutineContext by lazy {
        Dispatchers.Main + job
    }


    companion object {
        const val RC_SIGN_IN = 1132
    }

    abstract val model: BaseViewModel<S>
    abstract val layoutRes: Int?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutRes?.let {
            setContentView(it)
        }
    }

    override fun onStart() {
        super.onStart()

        dataJob = launch {
            model.getViewState().consumeEach {
                renderData(it)
            }
        }

        errorJob =launch {
            model.getErrors().consumeEach {
                renderError(it)
            }
        }
    }

    abstract fun renderData(data: S)

    private fun renderError(error: Throwable) {
        when(error) {
            is NoAuthException -> startLogin()
            else -> error.message?.let {showError(it)}
        }
    }

    private fun startLogin() {
        val providers = listOf(
            AuthUI.IdpConfig.GoogleBuilder().build()
        )
        startActivityForResult(
            AuthUI.getInstance()
                .createSignInIntentBuilder()
                .setLogo(R.drawable.panda)
                .setTheme(R.style.LoginStyle)
                .setAvailableProviders(providers)
                .build(),
            RC_SIGN_IN
        )
    }

    override fun onStop() {
        errorJob.cancel()
        dataJob.cancel()
        super.onStop()
    }

    override fun onDestroy() {
        super.onDestroy()
        coroutineContext.cancel()
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        if(requestCode == RC_SIGN_IN && resultCode != Activity.RESULT_OK) {
            finish()
        }
        super.onActivityResult(requestCode, resultCode, data)
    }

    protected fun showError(error: String){
        Toast.makeText(this, error, Toast.LENGTH_SHORT).show()
    }
}