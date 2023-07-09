package br.com.dev.springparallelnonblockinghttpclient.repository

import br.com.dev.springparallelnonblockinghttpclient.client.DogDataClient
import br.com.dev.springparallelnonblockinghttpclient.model.DogData
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import feign.FeignException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class DogDataRepository(@Autowired private val dogDataClient: DogDataClient) {

    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    suspend fun get(): Mono<DogData> {
        val dogData = try {
            dogDataClient.get().body!!
        } catch (e: FeignException) {
            logger.info(e.status().toString())
            logger.info(e.responseBody().toString())
            if (e.contentUTF8().trim().isNotBlank() && e.contentUTF8().trim() != "{}") {
                logger.info(
                    ObjectMapper().registerKotlinModule()
                        .readValue(e.contentUTF8(), DogData::class.java).toString()
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
            logger.info(e.status().toString())
            logger.info(e.responseBody().toString())
            if (e.contentUTF8().trim().isNotBlank() && e.contentUTF8().trim() != "{}") {
                logger.info(
                    ObjectMapper().registerKotlinModule()
                        .readValue(e.contentUTF8(), DogData::class.java).toString()
                )
            }
            throw Exception("Erro")
        }
        return dogData
    }

}