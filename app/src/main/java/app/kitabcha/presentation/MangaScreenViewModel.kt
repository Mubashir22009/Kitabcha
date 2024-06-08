package app.kitabcha.presentation

import androidx.lifecycle.ViewModel
import app.kitabcha.data.entity.ChapterEntity
import app.kitabcha.data.entity.MangaEntity
import app.kitabcha.data.entity.UserReadStatusEntity
import app.kitabcha.data.repository.CategoryMangaRepository
import app.kitabcha.data.repository.ChapterRepository
import app.kitabcha.data.repository.MangaRepository
import app.kitabcha.data.repository.UserReadStatusRepository
import app.kitabcha.source.AvailableSources
import app.kitabcha.source.model.SManga
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.async
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class MangaScreenViewModel
    @Inject
    constructor(
        private val mangaRepository: MangaRepository,
        private val chapterRepository: ChapterRepository,
        private val readStatusRepo: UserReadStatusRepository,
        private val MangaCategoryRepo: CategoryMangaRepository,
    ) : ViewModel() {
        private val _mangaChapters = MutableStateFlow(emptyList<ChapterEntity>())
        val mangaChapters = _mangaChapters.asStateFlow()

        private val _manga = MutableStateFlow<MangaEntity?>(null)
        val manga = _manga.asStateFlow()

        private val _loading = MutableStateFlow(true)
        val loading = _loading.asStateFlow()

        // these variables tell us the chapters, current user has already read
        private val _chaptersReadList = MutableStateFlow<List<Int>>(emptyList<Int>())
        var chaptersReadList = _chaptersReadList.asStateFlow()

        fun loading(value: Boolean) {
            _loading.tryEmit(value)
        }

        suspend fun deleteMangaFromCategory(
            catID: Int,
            MangaID: Int,
        ) {
            withContext(IO) {
                MangaCategoryRepo.delete(catID, MangaID)
            }
        }

        // function to get already read chapters of user
        suspend fun getAlreadyReadChapters(
            userID: Int,
            mangaID: Int,
        ) {
            withContext(IO) {
                _chaptersReadList.tryEmit(readStatusRepo.getMangaReadChaptersOfUser(userID, mangaID))
            }
        }

        suspend fun insertChapterInAlreadyRead(
            userReadID: Int,
            mangaReadId: Int,
            chapID: Int,
        ) {
            withContext(IO) {
                readStatusRepo.insert(UserReadStatusEntity(0, userReadID.toString(), mangaReadId.toString(), chapID))
            }
        }

        suspend fun getMangaFromDB(id: Int) {
            withContext(IO) {
                val chapters =
                    async {
                        chapterRepository.getMangaChapters(id)
                    }
                val manga =
                    async {
                        mangaRepository.getMangaFromMID(id)
                    }

                _mangaChapters.tryEmit(chapters.await())
                _manga.tryEmit(manga.await())
            }
        }

        suspend fun getMangaFromSource(mangaId: Int) {
            val source =
                manga.value?.let {
                    AvailableSources.sources[it.sourceID]
                } ?: return

            val remoteManga =
                with(manga.value!!) {
                    source.getMangaInfo(
                        SManga(
                            mangaTitle,
                            mangaURL,
                        ),
                    )
                }

            mangaRepository.update(
                remoteManga.let {
                    MangaEntity(
                        mangaId,
                        it.url,
                        it.title,
                        it.cover,
                        it.description,
                        it.tags.joinToString(),
                        it.author,
                        manga.value!!.sourceID,
                    )
                },
            )

            chapterRepository.insert(
                *remoteManga.chapters.map {
                    ChapterEntity(
                        ownerMangaID = mangaId,
                        chapterNum = it.number,
                        chapterURL = it.url,
                    )
                }.toTypedArray(),
            )
        }
    }
