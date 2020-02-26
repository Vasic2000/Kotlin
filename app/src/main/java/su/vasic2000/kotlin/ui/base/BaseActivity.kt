package su.vasic2000.kotlin.ui.base

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.Observer
import com.firebase.ui.auth.AuthUI
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import su.vasic2000.kotlin.R
import su.vasic2000.kotlin.data.errors.NoAuthException
import kotlin.coroutines.CoroutineContext

abstract class BaseActivity<T, S: BaseViewState<T>> : AppCompatActivity(), CoroutineScope {

    val job = Job()

    override val coroutineContext: CoroutineContext by lazy {
        Dispatchers.Main + job
    }


    companion object {
        const val RC_SIGN_IN = 1132
    }

    abstract val model: BaseViewModel<T, S>
    abstract val layoutRes: Int?

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        layoutRes?.let {
            setContentView(it)
        }
        model.getViewState().observe(this, object : Observer<S> {
            override fun onChanged(t: S?) {
                if(t == null) return
                if(t.error != null) renderError(t.error)
                renderData(t.data)
            }
        })
    }

    abstract fun renderData(data: T)

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