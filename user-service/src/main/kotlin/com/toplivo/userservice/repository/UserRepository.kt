package com.toplivo.userservice.repository

import com.toplivo.userservice.entity.UserEntity
import org.springframework.data.jpa.repository.JpaRepository

interface UserRepository : JpaRepository<UserEntity, Int> {

    fun findUserEntityByUsername(username: String): UserEntity?

    fun existsUserEntityByUsername(username: String): Boolean
}
