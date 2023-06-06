package br.com.dev.springparallelnonblockinghttpclient.client

import br.com.dev.springparallelnonblockinghttpclient.model.Post
import org.springframework.cloud.openfeign.FeignClient
import org.springframework.http.ResponseEntity
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RequestMethod

@FeignClient(name = "posts", url = "\${service.posts.url}")
interface PostClient {

    @RequestMapping(method = [RequestMethod.GET], value = ["/posts/{id}"])
    fun get(@PathVariable id: Int): ResponseEntity<Post>
}
