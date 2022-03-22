package org.quaerense.users.data.worker

import android.content.Context
import androidx.work.ListenableWorker
import androidx.work.WorkerFactory
import androidx.work.WorkerParameters
import org.quaerense.users.data.database.dao.UserDao
import org.quaerense.users.data.mapper.UserMapper
import org.quaerense.users.data.network.ApiService
import javax.inject.Inject

class LoadDataWorkerFactory @Inject constructor(
    private val apiService: ApiService,
    private val dao: UserDao,
    private val mapper: UserMapper
) : WorkerFactory() {

    override fun createWorker(
        appContext: Context, workerClassName: String, workerParameters: WorkerParameters
    ): ListenableWorker {
        return LoadDataWorker(appContext, workerParameters, apiService, dao, mapper)
    }
}