package sanmi.labs.zemogaandroidtest.di

import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import sanmi.labs.zemogaandroidtest.db.ApplicationDatabase
import sanmi.labs.zemogaandroidtest.network.PostService
import sanmi.labs.zemogaandroidtest.repository.PostRepository
import sanmi.labs.zemogaandroidtest.ui.detail.viewmodel.DetailPostViewModel
import sanmi.labs.zemogaandroidtest.ui.home.viewmodel.HomeViewModel
import sanmi.labs.zemogaandroidtest.util.ConnectionLiveData

val appModule = module {

    single { ConnectionLiveData(androidApplication()) }

    single { PostService.create() }
    single { ApplicationDatabase.getInstance(androidContext()).applicationDatabaseDao }
    single { PostRepository(get(), get(), get()) }

    viewModel { HomeViewModel(get()) }
    viewModel { DetailPostViewModel(get()) }
}