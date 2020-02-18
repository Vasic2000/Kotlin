package su.vasic2000.kotlin

import android.app.Application
import org.koin.android.ext.android.startKoin
import su.vasic2000.kotlin.di.appModule
import su.vasic2000.kotlin.di.mainModule
import su.vasic2000.kotlin.di.noteModule
import su.vasic2000.kotlin.di.splashModule

class App: Application() {
    override fun onCreate() {
        super.onCreate()
        startKoin(this, listOf(
            appModule,
            splashModule,
            noteModule,
            mainModule))
    }
}