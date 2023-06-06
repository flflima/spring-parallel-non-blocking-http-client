package br.com.dev.springparallelnonblockinghttpclient

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients

@SpringBootApplication
@EnableFeignClients(basePackages = ["br.com.dev.springparallelnonblockinghttpclient.client"])
class SpringParallelNonBlockingHttpClientApplication

fun main(args: Array<String>) {
	runApplication<SpringParallelNonBlockingHttpClientApplication>(*args)
}
