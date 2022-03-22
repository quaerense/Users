package org.quaerense.users.presentation

import android.app.Application
import org.quaerense.users.di.DaggerApplicationComponent

class UsersApp : Application() {

    val component by lazy {
        DaggerApplicationComponent.factory().create(this)
    }
}