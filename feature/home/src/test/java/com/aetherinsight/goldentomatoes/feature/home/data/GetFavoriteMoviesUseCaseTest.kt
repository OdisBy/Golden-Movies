package com.aetherinsight.goldentomatoes.feature.home.data

import app.cash.turbine.test
import com.aetherinsight.goldentomatoes.core.data.model.MovieGlobal
import com.aetherinsight.goldentomatoes.core.network.model.Resource
import com.aetherinsight.goldentomatoes.data.data.repositories.FavoriteRepository
import com.aetherinsight.goldentomatoes.feature.home.model.HomeMovie
import com.aetherinsight.goldentomatoes.testutils.robot.BaseRobot
import com.aetherinsight.goldentomatoes.testutils.robot.GIVEN
import com.aetherinsight.goldentomatoes.testutils.robot.RUN_UNIT_TEST
import com.aetherinsight.goldentomatoes.testutils.robot.THEN
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

internal class GetFavoriteMoviesUseCaseTest {

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
    fun `When API returns a MovieGlobal list should returns a list HomeMovie`() =
        runTest {
            RUN_UNIT_TEST(robot) {
                GIVEN { favoriteRepositorySuccessList() }
                THEN { useCaseShouldReturnsSuccessFlow() }
            }
        }

    @Test
    fun `When API returns a empty list should returns a empty HomeMovie list`() = runTest {
        RUN_UNIT_TEST(robot) {
            GIVEN { favoriteRepositoryEmptyList() }
            THEN { useCaseShouldReturnsSuccessFlowEmpty() }
        }
    }


    class Robot : BaseRobot {

        @MockK
        lateinit var favoriteRepository: FavoriteRepository

        private lateinit var getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase

        override fun setup() {
            MockKAnnotations.init(this, relaxUnitFun = true)

            getFavoriteMoviesUseCase = GetFavoriteMoviesUseCase(favoriteRepository)
        }

        override fun tearsDown() {

        }

        fun favoriteRepositorySuccessList() {
            coEvery { favoriteRepository.getFavoriteMovies() } returns flowOf(
                Resource.Success(dummyGlobalMovies)
            )
        }

        fun favoriteRepositoryEmptyList() {
            coEvery { favoriteRepository.getFavoriteMovies() } returns flowOf(
                Resource.Success(emptyList())
            )
        }

        suspend fun useCaseShouldReturnsSuccessFlow() {
            getFavoriteMoviesUseCase.invoke().test {
                val result = awaitItem()

                (result as Resource.Success).data shouldBe dummyHomeMovies

                awaitComplete()
            }
        }

        suspend fun useCaseShouldReturnsSuccessFlowEmpty() {
            getFavoriteMoviesUseCase.invoke().test {
                val result = awaitItem()

                (result as Resource.Success).data shouldBe emptyList()

                awaitComplete()
            }
        }

        private val dummyGlobalMovies = listOf(
            MovieGlobal(
                id = 1L,
                title = "Inception",
                description = "A thief who steals corporate secrets through the use of dream-sharing technology.",
                posterPath = "/inception_poster.jpg",
                favorite = true,
                scheduled = false
            ),
            MovieGlobal(
                id = 2L,
                title = "The Dark Knight",
                description = "When the menace known as The Joker emerges from his mysterious past.",
                posterPath = "/the_dark_knight_poster.jpg",
                favorite = true,
                scheduled = true
            ),
            MovieGlobal(
                id = 3L,
                title = "Interstellar",
                description = "A team of explorers travel through a wormhole in space.",
                posterPath = "/interstellar_poster.jpg",
                favorite = false,
                scheduled = false
            ),
            MovieGlobal(
                id = 4L,
                title = "The Matrix",
                description = "A computer hacker learns from mysterious rebels about the true nature of his reality.",
                posterPath = "/the_matrix_poster.jpg",
                favorite = false,
                scheduled = true
            ),
            MovieGlobal(
                id = 5L,
                title = "Fight Club",
                description = "An insomniac office worker and a devil-may-care soap maker form an underground fight club.",
                posterPath = "/fight_club_poster.jpg",
                favorite = true,
                scheduled = false
            )
        )

        private val dummyHomeMovies = listOf(
            HomeMovie(
                id = 1L,
                title = "Inception",
                description = "A thief who steals corporate secrets through the use of dream-sharing technology.",
                posterPath = "/inception_poster.jpg",
                favorite = true,
            ),
            HomeMovie(
                id = 2L,
                title = "The Dark Knight",
                description = "When the menace known as The Joker emerges from his mysterious past.",
                posterPath = "/the_dark_knight_poster.jpg",
                favorite = true,
            ),
            HomeMovie(
                id = 3L,
                title = "Interstellar",
                description = "A team of explorers travel through a wormhole in space.",
                posterPath = "/interstellar_poster.jpg",
                favorite = false,
            ),
            HomeMovie(
                id = 4L,
                title = "The Matrix",
                description = "A computer hacker learns from mysterious rebels about the true nature of his reality.",
                posterPath = "/the_matrix_poster.jpg",
                favorite = false,
            ),
            HomeMovie(
                id = 5L,
                title = "Fight Club",
                description = "An insomniac office worker and a devil-may-care soap maker form an underground fight club.",
                posterPath = "/fight_club_poster.jpg",
                favorite = true,
            )
        )

    }
}