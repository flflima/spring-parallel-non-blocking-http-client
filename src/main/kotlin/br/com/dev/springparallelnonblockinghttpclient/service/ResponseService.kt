package br.com.dev.springparallelnonblockinghttpclient.service

import br.com.dev.springparallelnonblockinghttpclient.model.Response

interface ResponseService {

    suspend fun get(id: Int): Response
    fun getBlocking(id: Int): Response
}