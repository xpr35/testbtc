package com.task.btc.service

import com.task.btc.dto.RecordDto
import com.task.btc.dto.TimeDurationDto
import com.task.btc.persistence.entity.RecordEntity
import com.task.btc.persistence.repository.RecordRepository
import org.springframework.stereotype.Service

@Service
class WalletServiceV2(
        private val recordRepository: RecordRepository
) {
    fun saveRecord(recordDto: RecordDto): RecordDto? =
            recordRepository.save(RecordEntity(
                    datetime = recordDto.datetime,
                    amount = recordDto.amount
            )).let { recordEntity ->
                RecordDto(recordEntity.datetime, recordEntity.amount)
            }

    fun getRecord(duration: TimeDurationDto): List<RecordDto> =
            recordRepository.getRecords(duration.startDatetime,
                    duration.endDatetime).map { recordEntity ->
                RecordDto(recordEntity.datetime, recordEntity.amount)
            }.toList()
}