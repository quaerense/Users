package org.quaerense.users.data.worker

import android.content.Context
import androidx.work.*
import org.quaerense.users.data.database.dao.UserDao
import org.quaerense.users.data.mapper.UserMapper
import org.quaerense.users.data.network.ApiService
import javax.inject.Inject

class LoadDataWorker @Inject constructor(
    context: Context,
    params: WorkerParameters,
    private val apiService: ApiService,
    private val dao: UserDao,
    private val mapper: UserMapper
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        try {
            val userContainerDto = apiService.getUserList()
            val totalPages = userContainerDto.totalPages ?: 1
            dao.deleteAll()
            dao.addAll(mapper.mapDtoListToEntityList(userContainerDto.users))

            for (page in 2..totalPages) {
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
            return OneTimeWorkRequestBuilder<LoadDataWorker>()
                .setConstraints(makeConstraints())
                .build()
        }

        private fun makeConstraints(): Constraints {
            return Constraints.Builder()
                .setRequiredNetworkType(NetworkType.CONNECTED)
                .build()
        }
    }

}