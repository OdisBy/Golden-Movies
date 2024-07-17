package com.odisby.goldentomatoes.feature.home.data

import app.cash.turbine.test
import com.odisby.goldentomatoes.core.network.model.Resource
import com.odisby.goldentomatoes.data.data.model.MovieGlobal
import com.odisby.goldentomatoes.data.data.repositories.DiscoverRepository
import com.odisby.goldentomatoes.feature.home.model.HomeMovie
import com.odisby.goldentomatoes.testutils.robot.BaseRobot
import com.odisby.goldentomatoes.testutils.robot.GIVEN
import com.odisby.goldentomatoes.testutils.robot.RUN_UNIT_TEST
import com.odisby.goldentomatoes.testutils.robot.THEN
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Assert.fail
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
    fun `API Return a Resource Success of List Global should return Resource Success with List HomeMovie`() =
        runTest {
            RUN_UNIT_TEST(robot) {
                GIVEN { discoverRepositorySuccessList() }
                THEN { useCaseShouldReturnsSuccessFlow() }
            }
        }

    @Test
    fun `When API returns Resource Error should return Resource Error with same text`() = runTest {
        RUN_UNIT_TEST(robot) {
            GIVEN { discoverRepositoryError() }
            THEN { useCaseShouldReturnsErrorFlow() }
        }
    }


    class Robot : BaseRobot {
        companion object {
            const val MOCKED_ERROR_MESSAGE = "Mocked Error in Tests"
        }

        @MockK
        lateinit var discoverRepository: DiscoverRepository

        private lateinit var getDiscoverMoviesUseCase: GetDiscoverMoviesUseCase

        override fun setup() {
            MockKAnnotations.init(this, relaxUnitFun = true)

            getDiscoverMoviesUseCase = GetDiscoverMoviesUseCase(discoverRepository)
        }

        override fun tearsDown() {

        }

        fun discoverRepositorySuccessList() {
            coEvery { discoverRepository.getDiscoverMovies() } returns flowOf(
                Resource.Success(
                    dummyGlobalMovies
                )
            )
        }

        fun discoverRepositoryError() {
            coEvery { discoverRepository.getDiscoverMovies() } returns flowOf(
                Resource.Error(MOCKED_ERROR_MESSAGE)
            )
        }

        suspend fun useCaseShouldReturnsSuccessFlow() {
            getDiscoverMoviesUseCase.invoke().test {
                val result = awaitItem()
                if (result is Resource.Success) {
                    result.data shouldBe dummyHomeMovies
                } else {
                    fail("Expected Resource.Success but got $result")
                }
                awaitComplete()
            }
        }

        suspend fun useCaseShouldReturnsErrorFlow() {
            getDiscoverMoviesUseCase.invoke().test {
                val result = awaitItem()
                if (result is Resource.Error) {
                    result.message shouldBe MOCKED_ERROR_MESSAGE
                } else {
                    fail("Expected Resource.Error but got $result")
                }
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