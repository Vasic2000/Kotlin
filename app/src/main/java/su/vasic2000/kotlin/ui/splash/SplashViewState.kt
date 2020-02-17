package su.vasic2000.kotlin.ui.splash

import su.vasic2000.kotlin.ui.base.BaseViewState

class SplashViewState(authenticated: Boolean? = null,
                      error: Throwable? = null) : BaseViewState<Boolean?>(authenticated, error)