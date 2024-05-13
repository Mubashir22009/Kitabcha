package app.kitabcha.browseScreen

import androidx.compose.runtime.mutableStateOf
import androidx.lifecycle.ViewModel
import app.kitabcha.data.repository.MangaRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.combine
import javax.inject.Inject
import

@HiltViewModel
class BrowseScreenViewModel @Inject constructor(
    //TODO :  Add Manga repository in which we are going to inject
    private val repository: MangaRepository
): ViewModel() {

    private val _searched_manga = MutableStateFlow("")
    val manga_searched = _searched_manga.asStateFlow()

    private val _isSearching = MutableStateFlow(false)
    val searchingInProgress = _isSearching.asStateFlow()

    // manga list will be the list we will receive from the server with respect to our query
    private val mangaList = MutableStateFlow(listOf<Int>()) // TODO: Add data type of this list

    // mangasFoundBySearch
    val mangasFoundBySearch = manga_searched.combine(mangaList)
    { text, mangaFoundBySearch ->
        // the code in this block will run when either the "manga_searched" variable changes or "mangaList" changes
        // and the final result will be in the "mangaFoundBySearch" variable
        if(text.isBlank()) // if list is empty jut display empty
        {
            // mangasFoundBySearch
        }else
        {
            mangasFoundBySearch.filter{
                // here we will add our query with 'it.ourquery'
            }
        }
    }


    // this will detect when something changes in the ui of our search bar
    fun onSearchTextChange(text: String)
    {
        _searched_manga.value = text
    }



}