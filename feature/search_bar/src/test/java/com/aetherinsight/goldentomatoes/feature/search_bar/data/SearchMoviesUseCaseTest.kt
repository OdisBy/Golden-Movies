package com.aetherinsight.goldentomatoes.feature.search_bar.data

import app.cash.turbine.test
import com.aetherinsight.goldentomatoes.core.data.model.SearchMovie
import com.aetherinsight.goldentomatoes.data.data.model.SearchMovieRemote
import com.aetherinsight.goldentomatoes.data.data.repositories.SearchMoviesRepository
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

internal class SearchMoviesUseCaseTest {

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
    fun `When API returns a SearchMovieRemote list should returns a ordered by favorite SearchMovie list`() =
        runTest {
            RUN_UNIT_TEST(robot) {
                GIVEN { searchMoviesRepositoryReturnsUnordered(Robot.QUERY_TEST_1) }
                THEN { useCaseShouldSuccessReturn(Robot.QUERY_TEST_1) }
            }
        }

    @Test
    fun `When Query does not match any movie should returns an empty list`() =
        runTest {
            RUN_UNIT_TEST(robot) {
                GIVEN { searchMoviesRepositoryReturnsUnordered(Robot.QUERY_TEST_2) }
                THEN { useCaseShouldEmptyReturn(Robot.QUERY_TEST_2) }
            }
        }


    class Robot : BaseRobot {

        companion object {
            const val QUERY_TEST_1 = "The M"
            const val QUERY_TEST_2 = "RandomText"
        }

        @MockK
        lateinit var searchMoviesRepository: SearchMoviesRepository

        private lateinit var searchMoviesUseCase: SearchMoviesUseCase

        override fun setup() {
            MockKAnnotations.init(this, relaxUnitFun = true)

            searchMoviesUseCase = SearchMoviesUseCase(searchMoviesRepository)
        }

        override fun tearsDown() {

        }

        fun searchMoviesRepositoryReturnsUnordered(query: String) {
            coEvery { searchMoviesRepository.searchMovies(query) } returns flowOf(
                dummySearchMoviesRemote.filter { it.title.contains(query, ignoreCase = true) }
            )
        }

        suspend fun useCaseShouldSuccessReturn(query: String) {
            searchMoviesUseCase.invoke(query).test {
                val result = awaitItem()

                result shouldBe dummySearchMovies.filter {
                    it.title.contains(
                        query,
                        ignoreCase = true
                    )
                }.sortedByDescending { it.favorite }

                awaitComplete()
            }
        }

        suspend fun useCaseShouldEmptyReturn(query: String) {
            searchMoviesUseCase.invoke(query).test {
                val result = awaitItem()

                result shouldBe emptyList<SearchMovie>()

                awaitComplete()
            }
        }


        private val dummySearchMoviesRemote = listOf(
            SearchMovieRemote(
                id = 6L,
                title = "The Matrix 3",
                favorite = false,
            ),
            SearchMovieRemote(
                id = 2L,
                title = "The Dark Knight",
                favorite = false,
            ),
            SearchMovieRemote(
                id = 1L,
                title = "Inception",
                favorite = false
            ),
            SearchMovieRemote(
                id = 4L,
                title = "The Matrix",
                favorite = false,
            ),
            SearchMovieRemote(
                id = 3L,
                title = "Interstellar",
                favorite = false,
            ),
            SearchMovieRemote(
                id = 7L,
                title = "Fight Club",
                favorite = true,
            ),
            SearchMovieRemote(
                id = 5L,
                title = "The Matrix 2",
                favorite = true,
            ),
        )

        private val dummySearchMovies = listOf(
            SearchMovie(
                id = 6L,
                title = "The Matrix 3",
                favorite = false,
            ),
            SearchMovie(
                id = 2L,
                title = "The Dark Knight",
                favorite = false,
            ),
            SearchMovie(
                id = 1L,
                title = "Inception",
                favorite = false
            ),
            SearchMovie(
                id = 4L,
                title = "The Matrix",
                favorite = false,
            ),
            SearchMovie(
                id = 3L,
                title = "Interstellar",
                favorite = false,
            ),
            SearchMovie(
                id = 5L,
                title = "The Matrix 2",
                favorite = true,
            ),
            SearchMovie(
                id = 7L,
                title = "Fight Club",
                favorite = true,
            ),
        )

    }
}