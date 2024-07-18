package com.odisby.goldentomatoes.feature.details.data

import com.odisby.goldentomatoes.data.data.repositories.FavoriteRepository
import com.odisby.goldentomatoes.feature.details.model.MovieDetails
import com.odisby.goldentomatoes.testutils.robot.BaseRobot
import com.odisby.goldentomatoes.testutils.robot.GIVEN
import com.odisby.goldentomatoes.testutils.robot.RUN_UNIT_TEST
import com.odisby.goldentomatoes.testutils.robot.THEN
import com.odisby.goldentomatoes.testutils.robot.WHEN
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Test

class SaveMoviesUseCaseTest {

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
    fun `invokeUseCaseWithoutFavorite should call addFavoriteMovie`() = runTest {
        RUN_UNIT_TEST(robot) {
            WHEN { invokeUseCaseWithoutFavorite() }
            THEN { assertCallAddFavorite() }
        }
    }

    @Test
    fun `invokeUseCaseWithFavorite should call removeFavorite`() = runTest {
        RUN_UNIT_TEST(robot) {
            WHEN { invokeUseCaseWithFavorite() }
            THEN { assertCallRemoveFavorite() }
        }
    }

    class Robot : BaseRobot {
        @MockK
        private lateinit var favoriteRepository: FavoriteRepository

        private lateinit var saveMoviesUseCase: SaveMoviesUseCase

        override fun setup() {
            MockKAnnotations.init(this, relaxUnitFun = true)

            saveMoviesUseCase = SaveMoviesUseCase(favoriteRepository)

            coEvery {
                favoriteRepository.addFavoriteMovie(any())
            } returns Unit

            coEvery {
                favoriteRepository.removeFavoriteMovie(any())
            } returns Unit
        }

        override fun tearsDown() {

        }

        suspend fun invokeUseCaseWithoutFavorite() {
            saveMoviesUseCase.invoke(dumbMovieDetails1)
        }

        suspend fun invokeUseCaseWithFavorite() {
            saveMoviesUseCase.invoke(dumbMovieDetails2)
        }

        fun assertCallRemoveFavorite() {
            coVerify(exactly = 1) {
                favoriteRepository.removeFavoriteMovie(dumbMovieDetails2.id)
            }
            coVerify(exactly = 0) {
                favoriteRepository.addFavoriteMovie(dumbMovieDetails1.toGlobalMovie())
            }
        }

        fun assertCallAddFavorite() {
            coVerify(exactly = 0) {
                favoriteRepository.removeFavoriteMovie(dumbMovieDetails2.id)
            }
            coVerify(exactly = 1) {
                favoriteRepository.addFavoriteMovie(dumbMovieDetails1.toGlobalMovie())
            }
        }


        private val dumbMovieDetails1 = MovieDetails(
            id = 1L,
            title = "Movie 1",
            description = "Description 1",
            posterPath = "/path/to/poster1.jpg",
            favorite = false,
            scheduled = false
        )

        private val dumbMovieDetails2 = MovieDetails(
            id = 2L,
            title = "Movie 2",
            description = "Description 2",
            posterPath = "/path/to/poster2.jpg",
            favorite = true,
            scheduled = true
        )
    }
}