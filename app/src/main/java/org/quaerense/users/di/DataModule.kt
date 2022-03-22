package org.quaerense.users.di

import android.app.Application
import dagger.Binds
import dagger.Module
import dagger.Provides
import org.quaerense.users.data.database.AppDatabase
import org.quaerense.users.data.database.dao.UserDao
import org.quaerense.users.data.repository.UserRepositoryImpl
import org.quaerense.users.domain.repository.UserRepository

@Module
interface DataModule {

    @Binds
    fun bindCoinRepository(impl: UserRepositoryImpl): UserRepository

    companion object {

        @Provides
        fun provideUserDao(
            application: Application
        ): UserDao {
            return AppDatabase.getInstance(application).userDao()
        }
    }
}