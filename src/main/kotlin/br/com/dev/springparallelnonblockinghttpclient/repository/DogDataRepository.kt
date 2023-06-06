package br.com.dev.springparallelnonblockinghttpclient.repository

import br.com.dev.springparallelnonblockinghttpclient.client.DogDataClient
import br.com.dev.springparallelnonblockinghttpclient.model.DogData
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import feign.FeignException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class DogDataRepository(@Autowired private val dogDataClient: DogDataClient) {

    fun get(): Mono<DogData> {
        val dogData = try {
            dogDataClient.get().body!!
        } catch (e: FeignException) {
            println(e.status())
            println(e.responseBody())
            if (e.contentUTF8().trim().isNotBlank() && e.contentUTF8().trim() != "{}") {
                println(
                    ObjectMapper().registerKotlinModule()
                        .readValue(e.contentUTF8(), DogData::class.java)
                )
            }
            throw Exception("Erro")
        }
        return Mono.just(dogData)
    }

    fun getBlocking(): DogData {
        val dogData = try {
            dogDataClient.get().body!!
        } catch (e: FeignException) {
            println(e.status())
            println(e.responseBody())
            if (e.contentUTF8().trim().isNotBlank() && e.contentUTF8().trim() != "{}") {
                println(
                    ObjectMapper().registerKotlinModule()
                        .readValue(e.contentUTF8(), DogData::class.java)
                )
            }
            throw Exception("Erro")
        }
        return dogData
    }

}