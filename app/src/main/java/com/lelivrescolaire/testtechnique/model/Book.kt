package com.lelivrescolaire.testtechnique.model

/**
 * Created by paour on 16/04/08.
 */
data class Book(
        val id: Int,
        val title: String,
        val cover: String,
        val pages: List<Page>
)

data class Page(
        val id: Int,
        val title: String,
        val cover: String,
        val content: String
)