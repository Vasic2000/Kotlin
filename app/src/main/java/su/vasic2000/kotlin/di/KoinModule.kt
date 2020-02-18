package su.vasic2000.kotlin.di

import com.google.firebase.auth.FirebaseAuth
import com.google.firebase.firestore.FirebaseFirestore
import org.koin.android.viewmodel.ext.koin.viewModel
import org.koin.dsl.module.module
import su.vasic2000.kotlin.data.NoteRepository
import su.vasic2000.kotlin.data.provider.FireStoreProvider
import su.vasic2000.kotlin.data.provider.RemoteDataProvider
import su.vasic2000.kotlin.ui.main.MainViewModel
import su.vasic2000.kotlin.ui.note.NoteViewModel
import su.vasic2000.kotlin.ui.splash.SplashViewModel

val appModule = module {
    single { FirebaseAuth.getInstance() }
    single { FirebaseFirestore.getInstance() }
    single{ FireStoreProvider(get(), get()) } bind RemoteDataProvider::class
    single { NoteRepository(get()) }
}

val splashModule = module {
    viewModel{ SplashViewModel(get())}
}

val mainModule = module {
    viewModel{ MainViewModel(get())}
}

val noteModule = module {
    viewModel{ NoteViewModel(get())}
}