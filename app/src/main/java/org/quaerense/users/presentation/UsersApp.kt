package org.quaerense.users.presentation

import android.app.Application
import androidx.work.Configuration
import org.quaerense.users.data.worker.LoadDataWorkerFactory
import org.quaerense.users.di.DaggerApplicationComponent
import javax.inject.Inject

class UsersApp : Application(), Configuration.Provider {

    @Inject
    lateinit var workerFactory: LoadDataWorkerFactory

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }

    override fun onCreate() {
        component.inject(this)
        super.onCreate()
    }

    override fun getWorkManagerConfiguration(): Configuration {
        return Configuration.Builder().setWorkerFactory(workerFactory).build()
    }
}