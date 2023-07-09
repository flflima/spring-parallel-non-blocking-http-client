package br.com.dev.springparallelnonblockinghttpclient.controller

import br.com.dev.springparallelnonblockinghttpclient.model.Response
import br.com.dev.springparallelnonblockinghttpclient.service.ResponseService
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import kotlin.system.measureTimeMillis

@RestController
@RequestMapping("data")
class ResponseController(@Autowired private val service: ResponseService) {

    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @GetMapping("/{id}")
    suspend fun get(@PathVariable id: Int): ResponseEntity<Response> {
        val response: ResponseEntity<Response>
        val time = measureTimeMillis { response = ResponseEntity.ok(service.get(id)) }
        logger.info("async = $time")
        return response
    }

    @GetMapping("blocking/{id}")
    fun getBlocking(@PathVariable id: Int): ResponseEntity<Response> {
        val init = System.currentTimeMillis()
        val response = ResponseEntity.ok(service.getBlocking(id))
        val end = System.currentTimeMillis()
        logger.info("sync = ${end - init}")

        return response
    }
}