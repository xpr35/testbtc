package com.task.btc.controller

import com.task.btc.dto.RecordDto
import com.task.btc.dto.TimeDurationDto
import com.task.btc.service.WalletService
import org.springframework.web.bind.annotation.*

@RestController
class BtcController(
        private val walletService: WalletService
) {
    @PostMapping("/saveRecord")
    fun saveRecord(@RequestBody record: RecordDto) =
            walletService.saveRecord(record)

    @GetMapping("/getRecord")
    fun getRecord(@RequestBody duration: TimeDurationDto) =
            walletService.getRecord(duration)
}