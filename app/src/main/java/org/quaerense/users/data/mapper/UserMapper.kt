package org.quaerense.users.data.mapper

import org.quaerense.users.data.database.model.UserDbModel
import org.quaerense.users.data.network.model.UserDto
import org.quaerense.users.domain.model.User

class UserMapper {

    fun mapDtoListToEntityList(dtoList: List<UserDto>?) =
        dtoList?.map {
            with(it) {
                UserDbModel(id, email, firstName, lastName, avatarUrl)
            }
        } ?: mutableListOf()

    fun mapDbModelToEntity(dbModel: UserDbModel): User = with(dbModel) {
        return User(id, email, firstName, lastName, avatarUrl)
    }

    fun mapDbModelListToEntityList(dbModelList: List<UserDbModel>?) =
        dbModelList?.map {
            mapDbModelToEntity(it)
        } ?: mutableListOf()

    fun mapEntityToDbModel(entity: User): UserDbModel = with(entity) {
        return UserDbModel(id, email, firstName, lastName, avatarUrl)
    }
}