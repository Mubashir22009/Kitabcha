package app.kitabcha.source

import app.kitabcha.source.model.Page
import app.kitabcha.source.model.SChapter
import app.kitabcha.source.model.SManga

interface Source {
    val name: String

    val id: Long

    suspend fun getListing(
        page: Int,
        search: String? = null,
    ): List<SManga>

    suspend fun getMangaInfo(manga: SManga): SManga

    suspend fun getPageList(chapter: SChapter): List<Page>

    suspend fun getImageUrl(page: Page): String
}
