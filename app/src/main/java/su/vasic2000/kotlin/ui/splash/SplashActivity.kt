package su.vasic2000.kotlin.ui.splash

import android.content.Context
import android.content.Intent
import android.os.Handler
import androidx.lifecycle.ViewModelProvider
import su.vasic2000.kotlin.ui.base.BaseActivity
import su.vasic2000.kotlin.ui.main.MainActivity

class SplashActivity : BaseActivity<Boolean?, SplashViewState>() {

    companion object {
        fun start(context: Context) = Intent(
            context,
            SplashActivity::class.java)
            .apply {
            context.startActivity(this)
        }
    }

    override val viewModel by lazy {
        ViewModelProvider(this).get(SplashViewModel::class.java)
    }

    override val layoutRes: Int? = null


    override fun renderData(data: Boolean?) {
        data?.takeIf { it }?.let {
            startMainActivity()
        }
    }

    override fun onResume() {
        super.onResume()
        Handler().postDelayed({ viewModel.requestUser() }, 1000)
//        viewModel.requestUser()
    }

    private fun startMainActivity() {
        MainActivity.start(this)
        finish()
    }
}