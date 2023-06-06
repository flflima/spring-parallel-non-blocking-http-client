package br.com.dev.springparallelnonblockinghttpclient.service

import br.com.dev.springparallelnonblockinghttpclient.model.Response
import br.com.dev.springparallelnonblockinghttpclient.repository.DogDataRepository
import br.com.dev.springparallelnonblockinghttpclient.repository.PostRepository
import kotlinx.coroutines.async
import kotlinx.coroutines.coroutineScope
import kotlinx.coroutines.reactive.awaitFirst
import org.bouncycastle.asn1.x500.style.RFC4519Style.title
import org.springframework.beans.factory.annotation.Autowired
import org.springframework.stereotype.Service
import org.springframework.web.servlet.function.ServerResponse.async

@Service
class ResponseServiceImpl(@Autowired private val postRepository: PostRepository,
@Autowired private val dogDataRepository: DogDataRepository) : ResponseService {

    override suspend fun get(id: Int): Response = coroutineScope {
        val post = async {
            postRepository.get(id).awaitFirst()
        }
        val dogData = async {
            dogDataRepository.get().awaitFirst()
        }
        return@coroutineScope Response(dogData.await().message, post.await().title)
    }

    override fun getBlocking(id: Int): Response {
        val post =             postRepository.getBlocking(id)
        val dogData =            dogDataRepository.getBlocking()
        return Response(dogData.message, post.title)
    }
}