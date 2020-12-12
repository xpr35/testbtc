package com.task.btc.persistence.repository

import com.task.btc.persistence.entity.RecordEntity
import org.springframework.data.jpa.repository.JpaRepository
import org.springframework.data.jpa.repository.Query
import org.springframework.data.repository.query.Param
import org.springframework.stereotype.Repository
import java.time.ZonedDateTime

@Repository
interface RecordRepository : JpaRepository<RecordEntity, Long> {
    fun getByDatetimeAfterAndDatetimeBefore(
            after: ZonedDateTime, before: ZonedDateTime): List<RecordEntity>

    fun getByDatetime(dateTime: ZonedDateTime): RecordEntity?
    fun getFirstByOrderByDatetimeDesc(): RecordEntity?

    @Query(REQ, nativeQuery = true)
    fun getRecords(@Param("after") after: ZonedDateTime,
                   @Param("before") before: ZonedDateTime): List<RecordEntity>

    companion object {
        private const val REQ = """SELECT amount "id", a.datetime, a.amount FROM (SELECT summed.datetime, sum(summed.amount) OVER (ORDER BY datetime) "amount" FROM (SELECT date_trunc('hour', record.datetime) "datetime", sum(record.amount) "amount" FROM record GROUP BY 1 ORDER BY 1) summed) a WHERE datetime BETWEEN :after AND :before"""
    }
}