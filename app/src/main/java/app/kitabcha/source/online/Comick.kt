package app.kitabcha.source.online

import app.kitabcha.source.HttpSource
import app.kitabcha.source.helper.get
import app.kitabcha.source.helper.mapJSON
import app.kitabcha.source.model.Page
import app.kitabcha.source.model.SChapter
import app.kitabcha.source.model.SManga
import org.json.JSONArray
import org.json.JSONObject
import org.jsoup.parser.Parser

class Comick : HttpSource() {
    override val name = "Comick"

    override val id = 1L

    override val domain = "comick.io"

    override suspend fun getListing(
        page: Int,
        search: String?,
    ): List<SManga> {
        val response =
            if (search.isNullOrBlank()) {
                val url = "https://api.$domain/v1.0/search?sort=follow&limit=20&page=$page&tachiyomi=true"

                client.get(url, headers)
            } else {
                if (page > 1) {
                    throw IllegalStateException("Comick doesn't support page>1 for search")
                }
                val url = "https://api.$domain/v1.0/search?limit=20&page=1&tachiyomi=true&q=${search.trim()}"

                client.get(url, headers)
            }

        return JSONArray(response.body.string()).mapJSON {
            SManga(
                url = it.getString("hid"),
                title = it.getString("title"),
                cover = it.getString("cover_url"),
            )
        }
    }

    override suspend fun getMangaInfo(manga: SManga): SManga {
        val url = "https://api.$domain/comic/${manga.url}?tachiyomi=true"
        val response = client.get(url, headers)
        val json = JSONObject(response.body.string())
        val comic = json.getJSONObject("comic")

        return SManga(
            title = comic.getString("title"),
            url = comic.getString("hid"),
            cover = comic.getString("cover_url"),
            author =
                json.getJSONArray("authors").mapJSON {
                    it.getString("name")
                }.joinToString(),
            artist =
                json.getJSONArray("artists").mapJSON {
                    it.getString("name")
                }.joinToString(),
            description =
                comic.getString("desc").run {
                    Parser.unescapeEntities(this, false)
                        .substringBefore("---")
                        .replace(markdownLinksRegex, "")
                        .replace(markdownItalicBoldRegex, "")
                        .replace(markdownItalicRegex, "")
                        .trim()
                },
            tags =
                comic.getJSONArray("md_comic_md_genres").mapJSON {
                    it.getJSONObject("md_genres").getString("name")
                },
            chapters = getChapters(manga.url),
        )
    }

    private val markdownLinksRegex = "\\[([^]]+)]\\(([^)]+)\\)".toRegex()
    private val markdownItalicBoldRegex = "\\*+\\s*([^*]*)\\s*\\*+".toRegex()
    private val markdownItalicRegex = "_+\\s*([^_]*)\\s*_+".toRegex()

    private suspend fun getChapters(hid: String): List<SChapter> {
        val url = "https://api.$domain/comic/$hid/chapters?lang=en&tachiyomi=true&limit=99999"
        val response = client.get(url, headers)
        return JSONObject(response.body.string()).getJSONArray("chapters").mapJSON { chapJson ->
            SChapter(
                url = chapJson.getString("hid"),
                number = chapJson.getString("chap").toFloat(),
            )
        }
    }

    override suspend fun getPageList(chapter: SChapter): List<Page> {
        val url = "https://api.$domain/chapter/${chapter.url}?tachiyomi=true"
        val response = client.get(url, headers)
        val json = JSONObject(response.body.string())

        return json.getJSONObject("chapter").getJSONArray("images").mapJSON {
            Page(it.getString("url"))
        }
    }
}
