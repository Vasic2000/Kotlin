package su.vasic2000.kotlin.ui.base

import androidx.appcompat.app.AppCompatActivity

abstract class BaseActivity<T, S: BaseViewState<T>> : AppCompatActivity() {
    abstract val viewModel: BaseViewModel<T, S>
}