package sanmi.labs.zemogaandroidtest.di

import org.koin.androidx.viewmodel.dsl.viewModel
import org.koin.dsl.module
import sanmi.labs.zemogaandroidtest.network.PostService
import sanmi.labs.zemogaandroidtest.ui.detail.viewmodel.DetailPostViewModel
import sanmi.labs.zemogaandroidtest.ui.home.viewmodel.HomeViewModel

val appModule = module {

    single { PostService.create() }

    viewModel { HomeViewModel(get()) }
    viewModel { DetailPostViewModel(get()) }
}