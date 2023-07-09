package br.com.dev.springparallelnonblockinghttpclient

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication
import org.springframework.cloud.openfeign.EnableFeignClients
import org.springframework.retry.annotation.EnableRetry

@SpringBootApplication
@EnableFeignClients(basePackages = ["br.com.dev.springparallelnonblockinghttpclient.client"])
@EnableRetry
class SpringParallelNonBlockingHttpClientApplication

fun main(args: Array<String>) {
	runApplication<SpringParallelNonBlockingHttpClientApplication>(*args)
}
