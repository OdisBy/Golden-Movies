package com.aetherinsight.goldentomatoes.feature.movielist.data

import com.aetherinsight.goldentomatoes.core.network.model.Resource
import com.aetherinsight.goldentomatoes.core.ui.constants.ListTypes
import com.aetherinsight.goldentomatoes.core.data.model.MovieGlobal
import com.aetherinsight.goldentomatoes.data.data.repositories.DiscoverRepository
import com.aetherinsight.goldentomatoes.data.data.repositories.FavoriteRepository
import com.aetherinsight.goldentomatoes.testutils.robot.BaseRobot
import com.aetherinsight.goldentomatoes.testutils.robot.GIVEN
import com.aetherinsight.goldentomatoes.testutils.robot.RUN_UNIT_TEST
import com.aetherinsight.goldentomatoes.testutils.robot.THEN
import com.aetherinsight.goldentomatoes.testutils.robot.WHEN
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

internal class GetListMoviesUseCaseTest {

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
    fun `When ListType is Discover should call DiscoverMovies Repository`() = runTest {
        RUN_UNIT_TEST(robot) {
            GIVEN { repositorySuccessDefault() }
            WHEN { invokeWithType(ListTypes.DISCOVER) }
            THEN { assertOnlyDiscoverRepositoryIsCalled(times = 1) }
        }
    }

    @Test
    fun `When ListType is Discover should call FavoriteMovies Repository`() = runTest {
        RUN_UNIT_TEST(robot) {
            GIVEN { repositorySuccessDefault() }
            WHEN { invokeWithType(ListTypes.FAVORITE) }
            THEN { assertOnlyFavoriteRepositoryIsCalled(times = 1) }
        }
    }

    class Robot : BaseRobot {
        @MockK
        lateinit var discoverRepository: DiscoverRepository

        @MockK
        lateinit var favoriteRepository: FavoriteRepository

        private lateinit var getListMoviesUseCase: GetListMoviesUseCase

        override fun setup() {
            MockKAnnotations.init(this, relaxUnitFun = true)

            getListMoviesUseCase =
                GetListMoviesUseCase(discoverRepository, favoriteRepository)
        }

        override fun tearsDown() {

        }

        fun repositorySuccessDefault() {
            coEvery {
                discoverRepository.getDiscoverMovies()
            } returns flowOf(Resource.Success(dumbMovieDiscover))

            coEvery {
                favoriteRepository.getFavoriteMovies()
            } returns flowOf(Resource.Success(dumbMovieDiscover))
        }

        suspend fun invokeWithType(type: ListTypes) {
            getListMoviesUseCase.invoke(type)
        }

        fun assertOnlyDiscoverRepositoryIsCalled(times: Int) {
            coVerify(exactly = times) { discoverRepository.getDiscoverMovies() }
        }

        fun assertOnlyFavoriteRepositoryIsCalled(times: Int) {
            coVerify(exactly = times) { favoriteRepository.getFavoriteMovies() }
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