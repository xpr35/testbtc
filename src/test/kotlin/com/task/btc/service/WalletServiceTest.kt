package com.task.btc.service

import com.nhaarman.mockitokotlin2.any
import com.nhaarman.mockitokotlin2.whenever
import com.task.btc.dto.RecordDto
import com.task.btc.dto.TimeDurationDto
import com.task.btc.persistence.entity.RecordEntity
import com.task.btc.persistence.repository.RecordRepository
import org.junit.Assert.assertEquals
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.InjectMocks
import org.mockito.Mock
import org.mockito.junit.MockitoJUnitRunner
import org.springframework.boot.test.context.SpringBootTest
import java.math.BigDecimal
import java.time.ZonedDateTime
import java.time.temporal.ChronoUnit

@SpringBootTest
@RunWith(MockitoJUnitRunner::class)
class WalletServiceTest {
    @Mock
    private lateinit var recordRepository: RecordRepository

    @InjectMocks
    private lateinit var walletService: WalletService

    private val DATETIME_ONE = ZonedDateTime.parse("2020-12-01T09:15:00+07:00")
    private val DATETIME_TWO = ZonedDateTime.parse("2020-12-01T09:30:00+07:00")
    private val DATETIME_THREE = ZonedDateTime.parse("2020-12-02T10:25:00+07:00")

    @Test
    fun `save record into empty db`() {
        val record = RecordDto(
                datetime = DATETIME_ONE.truncatedTo(ChronoUnit.HOURS),
                amount = BigDecimal.ONE
        )
        val entityWithId = RecordEntity(
                id = 1,
                datetime = DATETIME_ONE.truncatedTo(ChronoUnit.HOURS),
                amount = BigDecimal.ONE
        )
        whenever(recordRepository.save(any<RecordEntity>())).thenReturn(entityWithId)
        val response = walletService.saveRecord(record)
        assertEquals(record, response)
    }

    @Test
    fun `save record in db with entry in current hour`() {
        val record = RecordDto(
                datetime = DATETIME_TWO.truncatedTo(ChronoUnit.HOURS),
                amount = BigDecimal.valueOf(2L)
        )
        val entityWithIdPreviousThisHour = RecordEntity(
                id = 1,
                datetime = DATETIME_ONE.truncatedTo(ChronoUnit.HOURS),
                amount = BigDecimal.ONE
        )
        val entityWithIdCurrentThisHour = RecordEntity(
                id = 2,
                datetime = DATETIME_ONE.truncatedTo(ChronoUnit.HOURS),
                amount = BigDecimal.valueOf(2L)
        )
        whenever(recordRepository.getByDatetime(DATETIME_TWO.truncatedTo(ChronoUnit.HOURS)))
                .thenReturn(entityWithIdPreviousThisHour)
        whenever(recordRepository.save(any<RecordEntity>())).thenReturn(entityWithIdCurrentThisHour)
        val response = walletService.saveRecord(record)
        assertEquals(record, response)
    }

    @Test
    fun `save record in db with entry in older hour`() {
        val record = RecordDto(
                datetime = DATETIME_THREE,
                amount = BigDecimal.valueOf(2L)
        )
        val entityWithIdPreviousHour = RecordEntity(
                id = 1,
                datetime = DATETIME_ONE,
                amount = BigDecimal.ONE
        )
        val entityWithIdCurrentThisHour = RecordEntity(
                id = 2,
                datetime = DATETIME_THREE,
                amount = BigDecimal.valueOf(2L)
        )
        whenever(recordRepository.getFirstByOrderByDatetimeDesc()).thenReturn(entityWithIdPreviousHour)
        whenever(recordRepository.save(any<RecordEntity>())).thenReturn(entityWithIdCurrentThisHour)
        val response = walletService.saveRecord(record)
        assertEquals(record, response)
    }

    @Test
    fun `get record test`() {
        val duration = TimeDurationDto(
                startDatetime = DATETIME_ONE,
                endDatetime = DATETIME_THREE
        )
        val entitiesList = listOf(RecordEntity(
                id = 2,
                datetime = DATETIME_THREE,
                amount = BigDecimal.valueOf(2L)
        ))
        val recordsList = listOf(RecordDto(
                datetime = DATETIME_THREE,
                amount = BigDecimal.valueOf(2L)
        ))
        whenever(recordRepository.getByDatetimeAfterAndDatetimeBefore(DATETIME_ONE, DATETIME_THREE))
                .thenReturn(entitiesList)
        val response = walletService.getRecord(duration)
        assertEquals(recordsList, response)
    }
}