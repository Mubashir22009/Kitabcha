package app.kitabcha.source.model

class SManga(
    val title: String,
    val url: String,
    val cover: String = "",
    val author: String = "",
    val artist: String = "",
    val description: String = "",
    val tags: List<String> = emptyList(),
    val chapters: List<SChapter> = emptyList(),
)
