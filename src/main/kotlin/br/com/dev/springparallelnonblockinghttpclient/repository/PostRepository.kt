package br.com.dev.springparallelnonblockinghttpclient.repository

import br.com.dev.springparallelnonblockinghttpclient.client.PostClient
import br.com.dev.springparallelnonblockinghttpclient.model.Post
import com.fasterxml.jackson.databind.ObjectMapper
import com.fasterxml.jackson.module.kotlin.registerKotlinModule
import feign.FeignException
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Component
import reactor.core.publisher.Mono

@Component
class PostRepository(@Autowired private val client: PostClient) {

    fun get(id: Int): Mono<Post> {
        val post = try {
            client.get(id).body ?: getDefaultPost()
        } catch (e: FeignException) {
            println(e.status())
            println(e.responseBody())
            if (e.contentUTF8().trim().isNotBlank() && e.contentUTF8().trim() != "{}") {
                println(
                    ObjectMapper().registerKotlinModule()
                        .readValue(e.contentUTF8(), Post::class.java)
                )
            }

            if (e.status() in 400..499) {
                getDefaultPost()
            } else {
                throw Exception("Erro")
            }
        }
        return Mono.just(post)
    }

    fun getBlocking(id: Int): Post {
        val post = try {
            client.get(id).body ?: getDefaultPost()
        } catch (e: FeignException) {
            println(e.status())
            println(e.responseBody())
            if (e.contentUTF8().trim().isNotBlank() && e.contentUTF8().trim() != "{}") {
                println(
                    ObjectMapper().registerKotlinModule()
                        .readValue(e.contentUTF8(), Post::class.java)
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