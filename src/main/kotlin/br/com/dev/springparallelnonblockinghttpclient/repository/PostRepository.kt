package br.com.dev.springparallelnonblockinghttpclient.repository

import br.com.dev.springparallelnonblockinghttpclient.client.PostClient
import br.com.dev.springparallelnonblockinghttpclient.model.Post
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import feign.FeignException
import org.slf4j.Logger
import org.slf4j.LoggerFactory
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.retry.annotation.Backoff
import org.springframework.retry.annotation.Retryable
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class PostRepository(@Autowired private val client: PostClient) {

    val logger: Logger = LoggerFactory.getLogger(this::class.java)

    @Retryable(value = [FeignException::class], maxAttempts = 3, backoff = Backoff(delay = 100))
    suspend fun get(id: Int): Mono<Post> {
        val post = try {
            client.get(id).body ?: getDefaultPost()
        } catch (e: FeignException) {
            logger.info(e.status().toString())
//            logger.info(e.responseBody().toString())
            if (e.contentUTF8().trim().isNotBlank() && e.contentUTF8().trim() != "{}") {
                logger.info(
                    ObjectMapper().registerKotlinModule()
                        .readValue(e.contentUTF8(), Post::class.java).toString()
                )
            }

//            if (e.status() in 400..499) {
//                getDefaultPost()
//            } else {
//                throw Exception("Erro")
//            }
            throw e
        }
        return Mono.just(post)
    }

    fun getBlocking(id: Int): Post {
        val post = try {
            client.get(id).body ?: getDefaultPost()
        } catch (e: FeignException) {
            logger.info(e.status().toString())
            logger.info(e.responseBody().toString())
            if (e.contentUTF8().trim().isNotBlank() && e.contentUTF8().trim() != "{}") {
                logger.info(
                    ObjectMapper().registerKotlinModule()
                        .readValue(e.contentUTF8(), Post::class.java).toString()
                )
            }

            if (e.status() in 400..499) {
                getDefaultPost()
            } else {
                throw Exception("Erro")
            }
        }
        return post
    }

    private fun getDefaultPost() = Post(0, 0, "", "")
}