package com.lelivrescolaire.testtechnique.model

import android.support.annotation.Keep

/**
 * Created by paour on 16/04/08.
 */
@Keep
data class Book(
        val id: Int,
        val title: String,
        val cover: String,
        val pages: List<Page>
) {
    val pageIndex: Map<Int, Page> by lazy {
        val index = mutableMapOf<Int, Page>()
        pages.forEach { page -> index.put(page.id, page) }
        index
    }
}

@Keep
data class Page(
        val id: Int,
        val title: String,
        val cover: String,
        val content: String
)