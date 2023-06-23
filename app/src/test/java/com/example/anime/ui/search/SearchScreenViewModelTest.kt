package com.example.anime.ui.search

import com.example.anime.type.MediaSort
import com.example.anime.type.MediaType
import com.example.anime.ui.models.UiAnimeListItem
import com.example.anime.ui.models.UiMediaSortFilter
import com.example.anime.ui.models.UiMediaType
import com.example.anime.ui.models.UiSortFilter
import com.example.anime.ui.utils.mapToCoreModel
import com.example.anime.ui.utils.mapToUiModel
import com.example.core.models.CoreAnimeListItem
import com.example.core.models.CoreMediaType
import com.example.core.models.CoreSearchResultAnime
import com.example.core.models.CoreSortFilter
import com.example.core.repository.usecases.local.deleteitemusecase.DeleteItemUseCase
import com.example.core.repository.usecases.local.getallidsusecase.GetAllIdsUseCase
import com.example.core.repository.usecases.local.insertitemusecase.InsertItemUseCase
import com.example.core.repository.usecases.remote.getanimelistbysearchusecase.GetAnimeListBySearchUseCase
import io.mockk.Runs
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.just
import io.mockk.mockk
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.launch
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class SearchScreenViewModelTest {

    private lateinit var viewModel: SearchScreenViewModel
    private lateinit var uiState: SearchUiState

    private val getAnimeListBySearchUseCase: GetAnimeListBySearchUseCase = mockk()
    private val getAllIdsUseCase: GetAllIdsUseCase = mockk()
    private val deleteItemUseCase: DeleteItemUseCase = mockk()
    private val insertItemUseCase: InsertItemUseCase = mockk()

    private val mockedCoreSearchResultAnime = CoreSearchResultAnime(
        hasNextPage = true,
        animeList = listOf(
            CoreAnimeListItem(
                id = 1,
                title = "TestTitle1",
                coverImage = "TestCoverImage1"
            ),
            CoreAnimeListItem(
                id = 2,
                title = "TestTitle2",
                coverImage = "TestCoverImage2"
            ),
            CoreAnimeListItem(
                id = 3,
                title = "TestTitle3",
                coverImage = "TestCoverImage3"
            )
        )
    )


    private val mockedIdList = listOf(1, 2, 3, 4, 5)
    private val mockedUiMediaSortFilter = CoreSortFilter(
        name = "TestingSortFilterName"
    )
    private val mockedCoreSortFilters = listOf(mockedUiMediaSortFilter)
    private val mockedSearchValueExecutor = "TestingSearchValueExecutor"
    private val mockedCoreMediaType = CoreMediaType(name = "Anime")
    private val mockedUiAnimeListItems = flowOf(listOf(
        UiAnimeListItem(
            id = 1,
            title = "TestTitle1",
            coverImage = "TestCoverImage1"
        ),
        UiAnimeListItem(
            id = 2,
            title = "TestTitle2",
            coverImage = "TestCoverImage2"
        ),
        UiAnimeListItem(
            id = 3,
            title = "TestTitle3",
            coverImage = "TestCoverImage3"
        )
    ))

    private val testDispatcher = UnconfinedTestDispatcher()

    @OptIn(ExperimentalCoroutinesApi::class)
    @Before
    fun setUp() = runBlocking {
        Dispatchers.setMain(testDispatcher)

        coEvery {
            getAnimeListBySearchUseCase(
                page = 1,
                query = mockedSearchValueExecutor,
                typeFilter = mockedCoreMediaType,
                mediaSort = mockedCoreSortFilters
            )
        } returns mockedCoreSearchResultAnime

        coEvery {
            getAllIdsUseCase()
        } returns flowOf(mockedIdList)

        coEvery { deleteItemUseCase(any()) } just Runs
        coEvery { insertItemUseCase(any()) } just Runs


        viewModel = SearchScreenViewModel(
            getAnimeListBySearchUseCase,
            getAllIdsUseCase,
            deleteItemUseCase,
            insertItemUseCase
        )
        uiState = viewModel.searchUiState
    }

    @OptIn(ExperimentalCoroutinesApi::class)
    @Test
    fun `favoriteIds is loaded correctly`() =
        runTest {
            val collectJob = launch(testDispatcher) {
                viewModel.searchUiState.favoriteIds.collect()
            }
            val newFavoriteItem = viewModel.searchUiState.favoriteIds.first()
            assertEquals(mockedIdList, newFavoriteItem)
            collectJob.cancel()
        }

    @Test
    fun `mediaSortFilters is loaded correctly`() =
        runTest {
            val collectJob = launch(testDispatcher) {
                viewModel.searchUiState.mediaSortFilters.collect()
            }
            val expectedSortFilter = MediaSort.values().mapNotNull { item ->
                if (item.rawValue == "UNKNOWN__") null else UiMediaSortFilter(item.mapToUiModel())
            }
            val newMediaSortFilter = viewModel.searchUiState.mediaSortFilters.first()
            assertEquals(expectedSortFilter, newMediaSortFilter)
            collectJob.cancel()
        }

    @Test
    fun `typeFilters is loaded correctly`() =
        runTest {
            val collectJob = launch(testDispatcher) {
                viewModel.searchUiState.typeFilters.collect()
            }
            val expectedTypeFilter = MediaType.values().mapNotNull { item ->
                if (item.rawValue == "UNKNOWN__") null else item.mapToUiModel()
            }
            val newMediaSortFilter = viewModel.searchUiState.typeFilters.first()
            expectedTypeFilter.forEachIndexed { index, uiMediaType ->
                    assertEquals(uiMediaType.name,newMediaSortFilter[index].name )
                }
            collectJob.cancel()
        }

    @Test
    fun `onQueryChange is called, searchValue is changed`() =
        runBlocking {
            val collectJob = launch(testDispatcher) {
                viewModel.searchUiState.searchValue.collect()
            }
            viewModel.searchUiState.onQueryChange("TestQuery1")
            val newQuery = viewModel.searchUiState.searchValue.first()
            assertEquals("TestQuery1", newQuery)
            collectJob.cancel()
        }

    @Test
    fun `If users types an space as first character, is omited`() =
        runBlocking {
            val collectJob = launch(testDispatcher) {
                viewModel.searchUiState.searchValue.collect()
            }
            viewModel.searchUiState.onQueryChange(" ")
            val newQuery = viewModel.searchUiState.searchValue.first()
            assertEquals("", newQuery)
            collectJob.cancel()
        }

    @Test
    fun `onFavoriteClick deletes item if finds item id in the id's list`() =
        runBlocking {
            val collectJob = launch(testDispatcher) {
                viewModel.searchUiState.favoriteIds.collect()
            }
            val newItemAnime = UiAnimeListItem(
                id = 3,
                title = "TestTitle3",
                coverImage = "TestCoverImage3"
            )
            uiState.onFavoriteClick(
                newItemAnime
            )
            coVerify { deleteItemUseCase(newItemAnime.mapToCoreModel()) }
            collectJob.cancel()
        }

    @Test
    fun `onFavoriteClick inserts item if doesn't find item id in the id's list`() =
        runBlocking {
            val collectJob = launch(testDispatcher) {
                viewModel.searchUiState.favoriteIds.collect()
            }
            val newItemAnime = UiAnimeListItem(
                id = 6,
                title = "TestTitle",
                coverImage = "TestCoverImage"
            )
            uiState.onFavoriteClick(
                newItemAnime
            )
            coVerify { insertItemUseCase(newItemAnime.mapToCoreModel()) }
            collectJob.cancel()
        }

    @Test
    fun `If user taps on a non selected typefilter, filter is selected`() =
        runBlocking {
            val collectJob = launch(testDispatcher) {
                viewModel.searchUiState.selectedTypeFilter.collect()
            }
            val newFilter = UiMediaType(name = "TestMediaTypeName")
            uiState.onTypeFilterClick(newFilter)
            val filterState = uiState.selectedTypeFilter.first()
            assertEquals(newFilter, filterState)
            collectJob.cancel()
        }

    @Test
    fun `If user taps on a selected typefilter, filter is unselected`() =
        runBlocking {
            val collectJob = launch(testDispatcher) {
                viewModel.searchUiState.selectedTypeFilter.collect()
            }
            val newFilter = UiMediaType(name = "TestMediaTypeName")
            uiState.onTypeFilterClick(newFilter)
            uiState.onTypeFilterClick(newFilter)
            val filterState = uiState.selectedTypeFilter.first()
            assertEquals(null, filterState)
            collectJob.cancel()
        }

    @Test
    fun `If user taps on a different typefilter, filter gets replaced`() =
        runBlocking {
            val collectJob = launch(testDispatcher) {
                viewModel.searchUiState.selectedTypeFilter.collect()
            }
            val newFilter1 = UiMediaType(name = "TestMediaTypeName1")
            val newFilter2 = UiMediaType(name = "TestMediaTypeName2")
            uiState.onTypeFilterClick(newFilter1)
            uiState.onTypeFilterClick(newFilter2)
            val filterState = uiState.selectedTypeFilter.first()
            assertEquals(newFilter2, filterState)
            collectJob.cancel()
        }

    @Test
    fun `If user taps on a non selected sortfilter, filter is selected in the list`() =
        runBlocking {
            val collectJob = launch(testDispatcher) {
                viewModel.searchUiState.mediaSortFilters.collect()
            }
            val sortFilterName = MediaSort.values()[0].name
            val incomingFilter = UiMediaSortFilter(
                filter = UiSortFilter(name = sortFilterName)
            )
            uiState.onSortFilterClick(incomingFilter)
            val filterState = uiState.mediaSortFilters.first().find { filter ->
                filter.filter.name == sortFilterName
            }
            assertEquals(true, filterState?.isSelected)
            collectJob.cancel()
        }

    @Test
    fun `If user taps on a selected sortfilter, filter is unselected in the list`() =
        runBlocking {
            val collectJob = launch(testDispatcher) {
                viewModel.searchUiState.mediaSortFilters.collect()
            }
            val sortFilterName = MediaSort.values()[0].name
            val incomingFilter = UiMediaSortFilter(
                filter = UiSortFilter(name = sortFilterName)
            )
            uiState.onSortFilterClick(incomingFilter)
            uiState.onSortFilterClick(incomingFilter)
            val filterState = uiState.mediaSortFilters.first().find { filter ->
                filter.filter.name == sortFilterName
            }
            assertEquals(false, filterState?.isSelected)
            collectJob.cancel()
        }
}
