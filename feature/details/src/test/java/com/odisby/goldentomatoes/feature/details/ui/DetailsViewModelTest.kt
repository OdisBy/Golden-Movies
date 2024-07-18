package com.odisby.goldentomatoes.feature.details.ui

import com.odisby.goldentomatoes.core.network.model.Resource
import com.odisby.goldentomatoes.feature.details.data.GetDetailsUseCase
import com.odisby.goldentomatoes.feature.details.data.SaveMoviesUseCase
import com.odisby.goldentomatoes.feature.details.data.ScheduleUseCase
import com.odisby.goldentomatoes.feature.details.model.MovieDetails
import com.odisby.goldentomatoes.feature.details.ui.DetailsViewModelTest.Robot.Companion.MOVIE_ID_1
import com.odisby.goldentomatoes.testutils.MainDispatcherRule
import com.odisby.goldentomatoes.testutils.robot.BaseRobot
import com.odisby.goldentomatoes.testutils.robot.GIVEN
import com.odisby.goldentomatoes.testutils.robot.RUN_UNIT_TEST
import com.odisby.goldentomatoes.testutils.robot.THEN
import com.odisby.goldentomatoes.testutils.robot.WHEN
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

class DetailsViewModelTest {

    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

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
    fun `loadMovieDetails with movieId should update state with movie details`() = runTest {
        RUN_UNIT_TEST(robot) {
            GIVEN { getMovieDetailsUseCaseSuccess(MOVIE_ID_1) }
            WHEN { loadMovieDetails(MOVIE_ID_1) }
            THEN { assertStateMovieDetailsIsUpdated(MOVIE_ID_1) }
        }
    }


    class Robot : BaseRobot {

        companion object {
            const val MOVIE_ID_1 = 1L
            const val MOVIE_ID_2 = 2L
            const val RANDOM_MOVIE_ID = -1L
        }


        @MockK
        private lateinit var getDetailsUseCase: GetDetailsUseCase

        @MockK
        private lateinit var scheduleUseCase: ScheduleUseCase

        @MockK
        private lateinit var saveMoviesUseCase: SaveMoviesUseCase

        private lateinit var detailsViewModel: DetailsViewModel

        override fun setup() {
            MockKAnnotations.init(this, relaxUnitFun = true)

            detailsViewModel =
                DetailsViewModel(getDetailsUseCase, scheduleUseCase, saveMoviesUseCase)
        }

        override fun tearsDown() = runTest {

        }

        fun loadMovieDetails(movieId: Long) {
            detailsViewModel.loadMovieDetails(movieId)
        }

        fun onNextRandomMovieClick() {
            detailsViewModel.onNextRandomMovieClick()
        }

        fun onFavoriteButtonClick() {
            detailsViewModel.onFavoriteButtonClick()
        }

        fun onNotificationButtonClick() {
            detailsViewModel.onNotificationButtonClick()
        }

        suspend fun getMovieDetailsUseCaseSuccess(movieId: Long) {
            val movieDetails = when (movieId) {
                MOVIE_ID_1 -> dumbMovieDetails1
                MOVIE_ID_2 -> dumbMovieDetails2
                24L -> dumbMovieDetails3
                else -> throw IllegalArgumentException("Invalid movieId: $movieId")
            }

            coEvery {
                getDetailsUseCase.invoke(movieId)
            } returns flowOf(Resource.Success(movieDetails))
        }

        fun getRandomMovieUseCaseSuccess() {
            coEvery {
                getDetailsUseCase.getRandomMovieDetails()
            } returns flowOf(Resource.Success(dumbMovieDetails3))
        }

        suspend fun getScheduleUseCase(movieId: Long) {
            scheduleUseCase.invoke(dumbMovieDetails1)
        }

        suspend fun getSaveMoviesUseCase(movieId: Long) {
            saveMoviesUseCase.invoke(dumbMovieDetails2)
        }

        fun assertStateMovieDetailsIsUpdated(movieId: Long) {
            val movieDetails = when (movieId) {
                MOVIE_ID_1 -> dumbMovieDetails1
                MOVIE_ID_2 -> dumbMovieDetails2
                24L -> dumbMovieDetails3
                else -> null
            }

            assert(detailsViewModel.state.value.movieDetails == movieDetails)
        }


        private val dumbMovieDetails1 = MovieDetails(
            id = MOVIE_ID_1,
            title = "Movie 1",
            description = "Description 1",
            posterPath = "/path/to/poster1.jpg",
            favorite = false,
            scheduled = false
        )

        private val dumbMovieDetails2 = MovieDetails(
            id = MOVIE_ID_2,
            title = "Movie 2",
            description = "Description 2",
            posterPath = "/path/to/poster2.jpg",
            favorite = true,
            scheduled = true
        )

        private val dumbMovieDetails3 = MovieDetails(
            id = 24L,
            title = "Movie Random",
            description = "Description Random",
            posterPath = "/path/to/posterrandom.jpg",
            favorite = false,
            scheduled = false
        )

    }
}