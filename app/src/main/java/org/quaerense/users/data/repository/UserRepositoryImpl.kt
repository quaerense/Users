package org.quaerense.users.data.repository

import android.app.Application
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations
import androidx.work.ExistingWorkPolicy
import androidx.work.WorkManager
import org.quaerense.users.data.database.dao.UserDao
import org.quaerense.users.data.mapper.UserMapper
import org.quaerense.users.data.worker.LoadDataWorker
import org.quaerense.users.domain.model.User
import org.quaerense.users.domain.repository.UserRepository
import javax.inject.Inject

class UserRepositoryImpl @Inject constructor(
    private val application: Application,
    private val dao: UserDao,
    private val mapper: UserMapper
) : UserRepository {

    override suspend fun get(id: Int): User = mapper.mapDbModelToEntity(dao.get(id))

    override fun getAll(): LiveData<List<User>> = Transformations.map(dao.getAll()) {
        mapper.mapDbModelListToEntityList(it)
    }

    override suspend fun edit(user: User) = dao.edit(mapper.mapEntityToDbModel(user))

    override suspend fun delete(user: User) = dao.delete(user.id)

    override fun loadData() {
        val workManager = WorkManager.getInstance(application)
        workManager.enqueueUniqueWork(
            LoadDataWorker.NAME,
            ExistingWorkPolicy.REPLACE,
            LoadDataWorker.makeRequest()
        )
    }
}