package com.task.btc.service

import com.task.btc.dto.RecordDto
import com.task.btc.persistence.entity.RecordEntity
import com.task.btc.dto.TimeDurationDto
import com.task.btc.persistence.repository.RecordRepository
import org.springframework.stereotype.Service
import java.time.temporal.ChronoUnit

@Service
class WalletService(
        private val recordRepository: RecordRepository
) {
    fun saveRecord(recordDto: RecordDto): RecordDto? =
            recordRepository.getByDatetime(
                    recordDto.datetime.truncatedTo(ChronoUnit.HOURS)
            ).let {
                if (it != null) {
                    recordRepository.save(
                            it.apply { amount = it.amount + recordDto.amount }
                    )
                } else {
                    recordRepository.getFirstByOrderByDatetimeDesc()
                            .let { inner ->
                                recordRepository.save(
                                        RecordEntity(
                                                datetime = recordDto.datetime.truncatedTo(ChronoUnit.HOURS),
                                                amount = if (inner != null)
                                                    inner.amount + recordDto.amount
                                                else
                                                    recordDto.amount
                                        )
                                )
                            }
                }
            }.let {
                RecordDto(
                        it.datetime,
                        it.amount
                )
            }

    fun getRecord(duration: TimeDurationDto): List<RecordDto> =
            recordRepository.getByDatetimeAfterAndDatetimeBefore(
                    duration.startDatetime,
                    duration.endDatetime
            ).map { recordEntity ->
                RecordDto(recordEntity.datetime, recordEntity.amount)
            }.toList()
}