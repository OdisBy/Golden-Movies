package com.odisby.goldentomatoes.feature.movielist.data

import app.cash.turbine.test
import com.odisby.goldentomatoes.core.network.model.Resource
import com.odisby.goldentomatoes.core.ui.constants.ListTypes
import com.odisby.goldentomatoes.data.data.model.MovieGlobal
import com.odisby.goldentomatoes.data.data.repositories.DiscoverRepository
import com.odisby.goldentomatoes.data.data.repositories.FavoriteRepository
import com.odisby.goldentomatoes.testutils.robot.BaseRobot
import com.odisby.goldentomatoes.testutils.robot.GIVEN
import com.odisby.goldentomatoes.testutils.robot.RUN_UNIT_TEST
import com.odisby.goldentomatoes.testutils.robot.THEN
import com.odisby.goldentomatoes.testutils.robot.WHEN
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

internal class GetDiscoverMoviesUseCaseTest {

    private val robot = Robot()

    @Before
    fun setup() {
        robot.setup()
    }

    @After
    fun tearDown() {
        robot.tearsDown()
    }

    @Test
    fun `When ListType is Discover should return DiscoverMovies`() = runTest {
        RUN_UNIT_TEST(robot) {
            GIVEN { repositorySuccessDefault() }
            THEN { invokeDiscoverReturnsDiscoverMovies() }
        }
    }

    class Robot : BaseRobot {
        @MockK
        lateinit var discoverRepository: DiscoverRepository

        @MockK
        lateinit var favoriteRepository: FavoriteRepository

        private lateinit var getDiscoverMoviesUseCase: GetDiscoverMoviesUseCase

        override fun setup() {
            MockKAnnotations.init(this, relaxUnitFun = true)

            getDiscoverMoviesUseCase =
                GetDiscoverMoviesUseCase(discoverRepository, favoriteRepository)
        }

        override fun tearsDown() {

        }

        fun repositorySuccessDefault() {
            coEvery {
                discoverRepository.getDiscoverMovies()
            } returns flowOf(Resource.Success(dumbMovieDiscover))

            coEvery {
                favoriteRepository.getFavoriteMovies()
            } returns flowOf(dumbMovieFavorite)
        }

        suspend fun invokeDiscoverReturnsDiscoverMovies() {
            getDiscoverMoviesUseCase.invoke(ListTypes.DISCOVER).test {
                val result = awaitItem()

                result shouldBe Resource.Success(dumbMovieDiscover)
            }
        }


        private val dumbMovieFavorite = listOf(
            MovieGlobal(
                id = 1,
                title = "Favorite Movie 1",
                description = "Description of Favorite Movie 1",
                posterPath = "/path/to/poster1.jpg",
                favorite = true,
                scheduled = false
            ),
            MovieGlobal(
                id = 2,
                title = "Favorite Movie 2",
                description = "Description of Favorite Movie 2",
                posterPath = "/path/to/poster2.jpg",
                favorite = true,
                scheduled = true
            ),
            MovieGlobal(
                id = 3,
                title = "Favorite Movie 3",
                description = "Description of Favorite Movie 3",
                posterPath = "/path/to/poster3.jpg",
                favorite = true,
                scheduled = false
            )
        )

        private val dumbMovieDiscover = listOf(
            MovieGlobal(
                id = 4,
                title = "Discover Movie 1",
                description = "Description of Discover Movie 1",
                posterPath = "/path/to/poster4.jpg",
                favorite = false,
                scheduled = true
            ),
            MovieGlobal(
                id = 5,
                title = "Discover Movie 2",
                description = "Description of Discover Movie 2",
                posterPath = "/path/to/poster5.jpg",
                favorite = false,
                scheduled = false
            ),
            MovieGlobal(
                id = 6,
                title = "Discover Movie 3",
                description = "Description of Discover Movie 3",
                posterPath = "/path/to/poster6.jpg",
                favorite = false,
                scheduled = true
            )
        )
    }
}