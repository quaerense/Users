package org.quaerense.users.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import org.quaerense.users.data.database.AppDatabase
import org.quaerense.users.data.mapper.UserMapper
import org.quaerense.users.data.worker.LoadDataWorker
import org.quaerense.users.domain.model.User
import org.quaerense.users.domain.repository.UserRepository

class UserRepositoryImpl(private val application: Application) : UserRepository {
    private val dao = AppDatabase.getInstance(application).userDao()
    private val mapper = UserMapper()

    override fun get(id: Int): LiveData<User> = Transformations.map(dao.get(id)) {
        mapper.mapDbModelToEntity(it)
    }

    override fun getAll(): LiveData<List<User>> = Transformations.map(dao.getAll()) {
        mapper.mapDbModelListToEntityList(it)
    }

    override suspend fun edit(user: User) = dao.edit(mapper.mapEntityToDbModel(user))

    override suspend fun delete(id: Int) = dao.delete(id)

    override fun loadData() {
        val workManager = WorkManager.getInstance(application)
        workManager.enqueueUniqueWork(
            LoadDataWorker.NAME,
            ExistingWorkPolicy.REPLACE,
            LoadDataWorker.makeRequest()
        )
    }
}