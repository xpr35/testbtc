package com.task.btc.controller

import com.task.btc.dto.RecordDto
import com.task.btc.dto.TimeDurationDto
import com.task.btc.persistence.entity.RecordEntity
import com.task.btc.service.WalletServiceV2
import org.springframework.web.bind.annotation.*

@RestController
class BtcController(
        private val walletService: WalletServiceV2,
) {
    @PostMapping("/saveRecord")
    fun saveRecord(@RequestBody record: RecordDto) =
            walletService.saveRecord(record)

    @GetMapping("/getRecord")
    fun getRecord(@RequestBody duration: TimeDurationDto) : List<RecordDto> =
            walletService.getRecord(duration)
}