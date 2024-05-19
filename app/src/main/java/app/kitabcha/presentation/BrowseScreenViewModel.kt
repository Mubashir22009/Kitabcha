package app.kitabcha.presentation

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.kitabcha.data.entity.CategoryEntity
import app.kitabcha.data.entity.CategoryMangaEntity
import app.kitabcha.data.entity.MangaEntity
import app.kitabcha.data.repository.CategoryMangaRepository
import app.kitabcha.data.repository.LibraryRepository
import app.kitabcha.data.repository.MangaRepository
import app.kitabcha.source.AvailableSources
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@HiltViewModel
class BrowseScreenViewModel
    @Inject
    constructor(
        // TODO :  Add Manga repository in which we are going to inject
        private val repository: MangaRepository,
        private val libraryRepository: LibraryRepository,
        private val categoryMangaRepository: CategoryMangaRepository,
        private val MangaRepo: MangaRepository,
    ) : ViewModel() {
        private var _page = MutableStateFlow(1) // page numbers for our browse screen
        var page = _page.asStateFlow()

        private var _currentManga = MutableStateFlow<MangaEntity?>(null) // variable used for getting manga clicked in
        var currentManga = _currentManga.asStateFlow()

        private var _currentMangaId = MutableStateFlow<Int?>(null)
        var currentMangaId = _currentMangaId.asStateFlow()

        val lazyListFlag = MutableStateFlow(false)

        private val _searchQuery = MutableStateFlow("")
        val searchQuery = _searchQuery.asStateFlow()

        // private val _isSearching = MutableStateFlow(false)
        // val searchingInProgress = _isSearching.asStateFlow()

        private var _categories = MutableStateFlow(listOf<CategoryEntity>())
        var categories = _categories.asStateFlow()

        // manga list will be the list we will receive from the server with respect to our query
        val remoteManga = MutableStateFlow(listOf<MangaEntity>()) // TODO: Add data type of this list

        fun getMangas(
            sourceID: Long,
            pagenumber: Int,
        ) {
            val source = AvailableSources.sources[sourceID]!!
            viewModelScope.launch {
                source.getListing(pagenumber)
                    .map {
                        MangaEntity(
                            mangaURL = it.url,
                            mangaTag = "",
                            mangaDesc = it.description,
                            mangaTitle = it.title,
                            mangaAuthor = it.author,
                            sourceID = sourceID,
                        )
                    }.also {
                        repository.insert(
                            *it.toTypedArray(),
                        )
                    }.also { remoteManga.tryEmit(it) }
                    .also { lazyListFlag.tryEmit(true) }
                    .also { _page.tryEmit(pagenumber + 1) }
            }
        }

        // function for getting categories from database
        suspend fun getCategory(userID: Int) {
            _categories.tryEmit(libraryRepository.getAllCategoriesOfUser(userID))
        }

        // this function is used for
        fun updateMangaEntity(Manga: MangaEntity) {
            _currentManga.tryEmit(Manga)
        }

        // TODO: This function is a simple kotlin function while i am trying to call a suspend function from a simple function

        suspend fun pushMangaInCategory(
            mangaId: Int,
            categoryId: Int,
        ) {
            categoryMangaRepository.insert(
                CategoryMangaEntity(
                    ownerCatID = categoryId,
                    mangID = mangaId,
                ),
            )
            makeMangaVarNull()
        }

        // 1st we have dummy manga id when we are pushing in database after that the data base will assign
        suspend fun getRealMangaID(
            MangaURL: String,
            SourceID: Long,
        ) {
            withContext(IO) {
                val mangaId = MangaRepo.getDBMangaFromSource(MangaURL, SourceID)
                _currentMangaId.tryEmit(mangaId)
            }
        }

        // this function makes the manga variable which we got on clicking on the text in manga list,
        // because this also triggers the dialog box
        fun makeMangaVarNull() {
            _currentManga.tryEmit(null)
            _currentMangaId.tryEmit(null)
        }

        // this will detect when something changes in the ui of our search bar
        fun onSearchTextChange(text: String) {
            _searchQuery.value = text
        }
    }
