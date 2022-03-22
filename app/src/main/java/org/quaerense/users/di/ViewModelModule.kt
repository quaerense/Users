package org.quaerense.users.di

import androidx.lifecycle.ViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap
import org.quaerense.users.presentation.viewmodel.MainViewModel
import org.quaerense.users.presentation.viewmodel.UserEditViewModel

@Module
interface ViewModelModule {

    @Binds
    @IntoMap
    @ViewModelKey(MainViewModel::class)
    fun bindMainViewModel(viewModel: MainViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(UserEditViewModel::class)
    fun bindUserEditViewModel(viewModel: UserEditViewModel): ViewModel
}