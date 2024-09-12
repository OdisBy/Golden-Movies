package com.aetherinsight.goldentomatoes.feature.details.ui

import com.aetherinsight.goldentomatoes.core.data.model.MovieGlobal
import com.aetherinsight.goldentomatoes.core.network.model.Resource
import com.aetherinsight.goldentomatoes.core.usecases.GetDetailsUseCase
import com.aetherinsight.goldentomatoes.feature.details.data.ScheduleUseCase
import com.aetherinsight.goldentomatoes.feature.details.ui.DetailsViewModelTest.Robot.Companion.MOVIE_ID_1
import com.aetherinsight.goldentomatoes.feature.details.ui.DetailsViewModelTest.Robot.Companion.MOVIE_ID_2
import com.aetherinsight.goldentomatoes.testutils.MainDispatcherRule
import com.aetherinsight.goldentomatoes.testutils.robot.BaseRobot
import com.aetherinsight.goldentomatoes.testutils.robot.GIVEN
import com.aetherinsight.goldentomatoes.testutils.robot.RUN_UNIT_TEST
import com.aetherinsight.goldentomatoes.testutils.robot.THEN
import com.aetherinsight.goldentomatoes.testutils.robot.WHEN
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
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
    fun `loadMovieDetails with movieId should update state with movie details`() = runTest(
        UnconfinedTestDispatcher()
    ) {
        RUN_UNIT_TEST(robot) {
            GIVEN { getMovieDetailsUseCaseSuccess(MOVIE_ID_1) }
            WHEN { loadMovieDetails(MOVIE_ID_1) }
            advanceUntilIdle()
            THEN { assertStateMovieDetailsIsUpdated(MOVIE_ID_2) }
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
        private lateinit var favoriteMovieUseCase: com.aetherinsight.goldentomatoes.core.usecases.FavoriteMovieUseCase

        private lateinit var detailsViewModel: DetailsViewModel

        override fun setup() {
            MockKAnnotations.init(this, relaxUnitFun = true)

            detailsViewModel =
                DetailsViewModel(getDetailsUseCase, scheduleUseCase, favoriteMovieUseCase)
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
            detailsViewModel.onNotificationButtonClick(10L)
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
            favoriteMovieUseCase.invoke(
                dumbMovieDetails2
            )
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


        private val dumbMovieDetails1 = MovieGlobal(
            id = MOVIE_ID_1,
            title = "Movie 1",
            description = "Description 1",
            posterPath = "/path/to/poster1.jpg",
            favorite = false,
            scheduled = false
        )

        private val dumbMovieDetails2 = MovieGlobal(
            id = MOVIE_ID_2,
            title = "Movie 2",
            description = "Description 2",
            posterPath = "/path/to/poster2.jpg",
            favorite = true,
            scheduled = true
        )

        private val dumbMovieDetails3 = MovieGlobal(
            id = 24L,
            title = "Movie Random",
            description = "Description Random",
            posterPath = "/path/to/posterrandom.jpg",
            favorite = false,
            scheduled = false
        )

    }
}