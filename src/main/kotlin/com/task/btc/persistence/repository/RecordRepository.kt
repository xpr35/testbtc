package com.task.btc.persistence.repository

import com.task.btc.persistence.entity.RecordEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.stereotype.Repository
import java.time.ZonedDateTime

@Repository
interface RecordRepository: JpaRepository<RecordEntity, Long> {
    fun getByDatetimeAfterAndDatetimeBefore(
            after: ZonedDateTime, before: ZonedDateTime): List<RecordEntity>

    fun getByDatetime(dateTime: ZonedDateTime): RecordEntity?
    fun getFirstByOrderByDatetimeDesc() : RecordEntity?
}