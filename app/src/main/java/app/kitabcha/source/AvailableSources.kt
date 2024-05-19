package app.kitabcha.source

import app.kitabcha.source.online.Comick

object AvailableSources {
    val sources = listOf(Comick()).associateBy { it.id }
}
