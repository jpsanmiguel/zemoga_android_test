package sanmi.labs.zemogaandroidtest.di

import org.koin.android.ext.koin.androidApplication
import org.koin.android.ext.koin.androidContext
import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import sanmi.labs.zemogaandroidtest.data.source.local.ApplicationDatabase
import sanmi.labs.zemogaandroidtest.data.source.remote.PostService
import sanmi.labs.zemogaandroidtest.data.DefaultPostRepository
import sanmi.labs.zemogaandroidtest.ui.detail.viewmodel.DetailPostViewModel
import sanmi.labs.zemogaandroidtest.ui.home.viewmodel.HomeViewModel
import sanmi.labs.zemogaandroidtest.util.ConnectionLiveData

val appModule = module {

    single { ConnectionLiveData(androidApplication()) }

    single { PostService.create() }
    single { ApplicationDatabase.getInstance(androidContext()).applicationDatabaseDao }
    single { DefaultPostRepository(get(), get(), get()) }

    viewModel { HomeViewModel(get()) }
    viewModel { DetailPostViewModel(get()) }
}