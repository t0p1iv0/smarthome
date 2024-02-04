package com.toplivo.userservice.repository

import com.toplivo.userservice.entity.OutboxMessageEntity
import org.springframework.data.jpa.repository.JpaRepository

interface OutboxMessageRepository : JpaRepository<OutboxMessageEntity, Int>