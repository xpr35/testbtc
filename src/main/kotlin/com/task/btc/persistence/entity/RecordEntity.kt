package com.task.btc.persistence.entity

import java.math.BigDecimal
import java.time.ZonedDateTime
import javax.persistence.*

@Entity
@Table(name = "record")
class RecordEntity (
    @Id
    @SequenceGenerator(
            name = "ID_GENERATOR",
            sequenceName = "record_message_seq",
            allocationSize = 1
    )
    @GeneratedValue(
            strategy = GenerationType.SEQUENCE,
            generator = "ID_GENERATOR")
    val id: Long? = null,

    @Column(columnDefinition= "TIMESTAMP WITH TIME ZONE")
    val datetime: ZonedDateTime,

    @Column
    var amount: BigDecimal
)