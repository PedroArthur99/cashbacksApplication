package com.boticario.cashback

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients
class CashbackApplication

fun main(args: Array<String>) {
	runApplication<CashbackApplication>(*args)
}
