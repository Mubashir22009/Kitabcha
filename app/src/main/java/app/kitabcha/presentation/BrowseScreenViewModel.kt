package app.kitabcha.presentation

import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import app.kitabcha.data.entity.CategoryEntity
import app.kitabcha.data.entity.CategoryMangaEntity
import app.kitabcha.data.entity.MangaEntity
import app.kitabcha.data.repository.CategoryMangaRepository
import app.kitabcha.data.repository.CategoryRepository
import app.kitabcha.data.repository.LibraryRepository
import app.kitabcha.data.repository.MangaRepository
import app.kitabcha.source.AvailableSources
import app.kitabcha.source.model.SManga
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Dispatchers.IO
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.withContext
import javax.inject.Inject


@HiltViewModel
class BrowseScreenViewModel @Inject constructor(
    //TODO :  Add Manga repository in which we are going to inject
    private val repository: MangaRepository,
    private val libraryRepository: LibraryRepository,
    private val categoryMangaRepository: CategoryMangaRepository,
    private val MangaRepo: MangaRepository
): ViewModel() {

    private var pageNumber =  MutableStateFlow(1) // page numbers for our browse screen
    var _pagenumber = pageNumber.asStateFlow()


    private var _currentManga = MutableStateFlow<MangaEntity?>(null) // variable used for getting manga clicked in
    var currentManga = _currentManga.asStateFlow()

    val lazyListFlag = MutableStateFlow(false)

    private val _searched_manga = MutableStateFlow("")
    val manga_searched = _searched_manga.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val searchingInProgress = _isSearching.asStateFlow()

    private var _ListOfCatagory = MutableStateFlow(listOf<CategoryEntity>())
    var ListOfCategory = _ListOfCatagory.asStateFlow()

    // manga list will be the list we will receive from the server with respect to our query
    val mangaList_toDisplay = MutableStateFlow(listOf<MangaEntity>()) // TODO: Add data type of this list

// FUNCTIONS: ++++++++++++++++++++++++++++++++++++++++=

    fun getMangas(sourceID: Long,pagenumber: Int)
    {
        val _source = AvailableSources.sources[sourceID]!!
        viewModelScope.launch {
            _source.getListing(pagenumber)
                .map { // TODO: pass page number properly
                MangaEntity(
                    mangaURL = it.url,
                    mangaTag = "",
                    mangaDesc = it.description,
                    mangaTitle = it.title,
                    mangaAuthor = it.author,
                    sourceID = 1L // TODO:
                )
            }.also { repository.insert(*it.toTypedArray()) } .also { mangaList_toDisplay.tryEmit(it) }.also{lazyListFlag.tryEmit(true)}
                .also{pageNumber.tryEmit(pagenumber+1)}
        }
    }

    // function for getting categories from database
    suspend fun getCategory(userID: Int)
    {
            _ListOfCatagory.tryEmit(libraryRepository.getAllCategoriesOfUser(userID))
    }

    // this function is used for
    fun updateMangaEntity (Manga: MangaEntity)
    {
        _currentManga.tryEmit(Manga)
    }

    // TODO: This function is a simple kotlin function while i am trying to call a suspend function from a simple function

    fun pushMangaInCategory(MangaID: MangaEntity?,CategoryID:Int)
    {
            runBlocking {
                if (MangaID != null) {
                    categoryMangaRepository.insert(CategoryMangaEntity(0, CategoryID, MangaID.mangaID ))
                }
            }
        _currentManga.tryEmit(null)

    }

    // 1st we have dummy manga id when we are pushing in database after that the data base will assign
    fun getRealMangaID (MangaURL: String,SourceID:Long) : Int
    {
         return MangaRepo.getDBMangaFromSource(MangaURL,SourceID)
    }

    // this function makes the manga variable which we got on clicking on the text in manga list,
    // because this also triggers the dialog box
    fun makeMangaVarNull ()
    {
        _currentManga.tryEmit(null)
    }


    // this will detect when something changes in the ui of our search bar
    fun onSearchTextChange(text: String)
    {
        _searched_manga.value = text
    }

}




