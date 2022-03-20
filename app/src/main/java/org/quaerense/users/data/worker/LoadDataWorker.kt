package org.quaerense.users.data.worker

import android.content.Context
import androidx.work.CoroutineWorker
import androidx.work.OneTimeWorkRequest
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkerParameters
import org.quaerense.users.data.database.AppDatabase
import org.quaerense.users.data.mapper.UserMapper
import org.quaerense.users.data.network.ApiFactory

class LoadDataWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    private val apiService = ApiFactory.apiService
    private val dao = AppDatabase.getInstance(context).userDao()
    private val mapper = UserMapper()

    override suspend fun doWork(): Result {
        try {
            val userContainerDto = apiService.getUserList()
            val totalPages = userContainerDto.totalPages ?: 0
            dao.deleteAll()
            dao.addAll(mapper.mapDtoListToEntityList(userContainerDto.users))

            for (page in 1..totalPages) {
                val userListDto = apiService.getUserList(page).users
                dao.addAll(mapper.mapDtoListToEntityList(userListDto))
            }
        } catch (e: Exception) {
            Result.failure()
        }

        return Result.success()
    }


    companion object {

        const val NAME = "LoadDataWorker"

        fun makeRequest(): OneTimeWorkRequest {
            return OneTimeWorkRequestBuilder<LoadDataWorker>().build()
        }
    }

}