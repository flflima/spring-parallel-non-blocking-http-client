package br.com.dev.springparallelnonblockinghttpclient.client

import br.com.dev.springparallelnonblockinghttpclient.model.DogData
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(name = "dogs", url = "\${service.dogs.url}")
interface DogDataClient {

    @RequestMapping(method = [RequestMethod.GET])
    fun get(): ResponseEntity<DogData>
}
