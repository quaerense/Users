package org.quaerense.users.data.mapper

import org.quaerense.users.data.database.model.UserDbModel
import org.quaerense.users.data.network.model.UserDto
import org.quaerense.users.domain.User

class UserMapper {

    fun mapDtoListToEntityList(dtoList: List<UserDto>?): List<UserDbModel> {
        val result = mutableListOf<UserDbModel>()
        if (dtoList == null) return result

        for (dto in dtoList) {
            with(dto) {
                result.add(UserDbModel(id, email, firstName, lastName, avatarUrl))
            }
        }

        return result
    }

    fun mapDbModelListToEntityList(dbModelList: List<UserDbModel>?): List<User> {
        val result = mutableListOf<User>()
        if (dbModelList == null) return result

        for (dbModel in dbModelList) {
            with(dbModel) {
                result.add(User(id, email, firstName, lastName, avatarUrl))
            }
        }

        return result
    }
}