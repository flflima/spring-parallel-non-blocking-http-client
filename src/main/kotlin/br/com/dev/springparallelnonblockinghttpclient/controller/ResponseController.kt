package br.com.dev.springparallelnonblockinghttpclient.controller

import br.com.dev.springparallelnonblockinghttpclient.model.Response
import br.com.dev.springparallelnonblockinghttpclient.service.ResponseService
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("data")
class ResponseController(@Autowired private val service: ResponseService) {

    @GetMapping("/{id}")
    suspend fun get(@PathVariable id: Int): ResponseEntity<Response> {
        return ResponseEntity.ok(service.get(id))
    }

    @GetMapping("blocking/{id}")
    fun getBlocking(@PathVariable id: Int): ResponseEntity<Response> {
        return ResponseEntity.ok(service.getBlocking(id))
    }
}