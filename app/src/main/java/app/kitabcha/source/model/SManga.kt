package app.kitabcha.source.model

class SManga(
    title: String,
    url: String,
    cover: String = "",
    author: String = "",
    artist: String = "",
    description: String = "",
    tags: List<String> = emptyList(),
    chapters: List<SChapter>
)