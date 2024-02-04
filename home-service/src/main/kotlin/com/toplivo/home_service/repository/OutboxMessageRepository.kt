package com.toplivo.home_service.repository

import com.toplivo.home_service.entity.OutboxMessageEntity
import org.springframework.data.jpa.repository.JpaRepository

interface OutboxMessageRepository : JpaRepository<OutboxMessageEntity, Int> {
}