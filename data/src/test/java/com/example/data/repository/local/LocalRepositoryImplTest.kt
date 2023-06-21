package com.example.data.repository.local

import com.example.core.models.CoreFavoriteAnime
import com.example.data.database.AnimeFavoriteEntity
import com.example.data.database.FavoriteAnimeDao
import com.example.data.utils.mapToCoreModel
import io.mockk.coEvery
import io.mockk.every
import io.mockk.mockk
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test

class LocalRepositoryImplTest {

    private lateinit var localRepositoryImpl: LocalRepositoryImpl

    private val fakeFavoriteAnimeDao: FavoriteAnimeDao = mockk()
    private val mockedAnimeFavoriteEntityList = listOf(
        AnimeFavoriteEntity(
            id = 1,
            coverImage = "TestCoverImage1",
            title = "TestTitle1"
        ),
        AnimeFavoriteEntity(
            id = 2,
            coverImage = "TestCoverImage2",
            title = "TestTitle2"
        ),
        AnimeFavoriteEntity(
            id = 3,
            coverImage = "TestCoverImage3",
            title = "TestTitle3"
        ),
        AnimeFavoriteEntity(
            id = 4,
            coverImage = "TestCoverImage4",
            title = "TestTitle4"
        )
    )
    private val mockedAnimeFavoriteCoreList = mockedAnimeFavoriteEntityList.mapToCoreModel()
    private val mockedIdList = listOf(1, 2, 3, 4)

    @Before
    fun setupTest() {
        val internalMockedAnimeFavoriteEntityList = mockedAnimeFavoriteEntityList.toMutableList()
        localRepositoryImpl = LocalRepositoryImpl(fakeFavoriteAnimeDao)

        every { fakeFavoriteAnimeDao.getAllFavorites() } returns flowOf(
            internalMockedAnimeFavoriteEntityList
        )
        every { fakeFavoriteAnimeDao.getAllIds() } returns flowOf(mockedIdList)
        coEvery { fakeFavoriteAnimeDao.deleteItem(any()) } answers { item ->
            internalMockedAnimeFavoriteEntityList.removeIf { (item.invocation.args[0] as AnimeFavoriteEntity).id == it.id }
        }
        coEvery { fakeFavoriteAnimeDao.insertItem(any()) } answers { item ->
            internalMockedAnimeFavoriteEntityList.add(item.invocation.args[0] as AnimeFavoriteEntity)
        }
    }

    @Test
    fun `When getting all favorites from database, items return correctly`() = runBlocking {
        localRepositoryImpl.getAllFavorites().collect { list ->
            assertEquals(list, mockedAnimeFavoriteCoreList)
        }
    }

    @Test
    fun `When getting all ids from database, ids return correctly`() = runBlocking {
        localRepositoryImpl.getAllIds().collect { list ->
            assertEquals(list, mockedIdList)
        }
    }

    @Test
    fun `When delete item from database, items return correctly`() = runBlocking {
        localRepositoryImpl.deleteItem(
            CoreFavoriteAnime(
                id = 4,
                coverImage = "TestCoverImage4",
                title = "TestTitle4"
            )
        )
        localRepositoryImpl.getAllFavorites().collect { list ->
            assertEquals(list, mockedAnimeFavoriteCoreList.take(3))
        }
    }

    @Test
    fun `When insert item to database, items return correctly`() = runBlocking {
        val newAnimeItem = CoreFavoriteAnime(
            id = 5,
            coverImage = "TestCoverImage5",
            title = "TestTitle5"
        )
        localRepositoryImpl.insertItem(newAnimeItem)
        localRepositoryImpl.getAllFavorites().collect { list ->
            assertEquals(list, mockedAnimeFavoriteCoreList + listOf(newAnimeItem))
        }
    }
}
