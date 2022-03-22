package org.quaerense.users.di

import android.app.Application
import dagger.BindsInstance
import dagger.Component
import org.quaerense.users.presentation.fragment.MainFragment
import org.quaerense.users.presentation.fragment.UserEditFragment

@Component(modules = [DataModule::class, ViewModelModule::class])
interface ApplicationComponent {

    fun inject(fragment: MainFragment)

    fun inject(fragment: UserEditFragment)

    @Component.Factory
    interface Factory {

        fun create(
            @BindsInstance application: Application
        ): ApplicationComponent
    }
}