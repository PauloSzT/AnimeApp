package com.example.data.repository.remote

import com.example.core.models.CoreAnimeCharacter
import com.example.core.models.CoreAnimeCharacterDetail
import com.example.core.models.CoreAnimeListItem
import com.example.core.models.CoreDetailAnime
import com.example.core.models.CoreMediaType
import com.example.core.models.CoreSearchResultAnime
import com.example.core.models.CoreSortFilter
import com.example.core.remote.RemoteService
import io.mockk.coEvery
import io.mockk.mockk
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class RemoteRepositoryImplTest {

    private lateinit var remoteRepositoryImpl: RemoteRepositoryImpl
    private val remoteService: RemoteService = mockk()

    private val mockedCoreSearchResultAnime = CoreSearchResultAnime(
        hasNextPage = true, animeList = listOf(
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
                id = 1,
                title = "TestTitle1",
                coverImage = "TestCoverImage1"
            )
        )
    )
    private val mockedCoreAnimeCharacter = CoreAnimeCharacter(
        id = 11,
        imageUrl = "TestingCharacterImageUrl1",
        name = "TestingCharacterName1"
    )
    private val mockedCoreDetailAnime = CoreDetailAnime(
        id = 1,
        coverImage = "TestingCoverImage1",
        title = "TestingTitle1",
        episodes = 100,
        score = 10,
        genres = listOf("anime", "manga"),
        englishName = "TestingEnglishName1",
        japaneseName = "TestingJapaneseName1",
        description = "TestingDescription1",
        characters = listOf(mockedCoreAnimeCharacter)
    )
    private val mockedCoreAnimeCharacterDetail = CoreAnimeCharacterDetail(
        name = "TestingCharacterDetailName1",
        imageUrl = "TestingCharacterDetailImageUrl1",
        description = "TestingCharacterDetailDescription1",
        gender = "TestingCharacterDetailGender1",
        dateOfBirth = "TestingCharacterDetailDateOfBirth1",
        age = "TestingCharacterDetailAge1",
        bloodType = "TestingCharacterDetailBloodtype1"
    )

    @Before
    fun setUp() {
        remoteRepositoryImpl = RemoteRepositoryImpl(remoteService)

        coEvery {
            remoteService.getAnimeListBySearch(
                any(),
                any(),
                any(),
                any()
            )
        } returns mockedCoreSearchResultAnime
        coEvery { remoteService.getAnimeById(any()) } returns mockedCoreDetailAnime
        coEvery { remoteService.getCharacterById(any()) } returns mockedCoreAnimeCharacterDetail
    }

    @Test
    fun `When getting animeList by search, list return correctly`() = runBlocking {
        val mockedCoreSortFilter = CoreSortFilter(name = "TestingSortFilterName1")
        val mockedCoreMediaType = CoreMediaType(name = "TestingMediaTypeName1")
        val newAnimeListBySearch = remoteRepositoryImpl.getAnimeListBySearch(
            page = 1,
            query = "goku",
            typeFilter = mockedCoreMediaType,
            mediaSort = listOf(mockedCoreSortFilter)
        )
        assertEquals(newAnimeListBySearch, mockedCoreSearchResultAnime)
    }

    @Test
    fun `When getting anime by id, item return correctly`() = runBlocking {
        val newAnimeById = remoteRepositoryImpl.getAnimeById(animeId = 1)
        assertEquals(newAnimeById, mockedCoreDetailAnime)
    }

    @Test
    fun `When getting character by id, character return correctly`() = runBlocking {
        val newCharacterById = remoteRepositoryImpl.getCharacterById(characterId = 11)
        assertEquals(newCharacterById, mockedCoreAnimeCharacterDetail)
    }
}
