package com.aetherinsight.goldentomatoes.core.usecases

import com.aetherinsight.goldentomatoes.core.data.model.MovieGlobal
import com.aetherinsight.goldentomatoes.data.data.repositories.DetailsRepository
import com.aetherinsight.goldentomatoes.data.data.repositories.FavoriteRepository
import com.aetherinsight.goldentomatoes.testutils.robot.BaseRobot
import com.aetherinsight.goldentomatoes.testutils.robot.RUN_UNIT_TEST
import com.aetherinsight.goldentomatoes.testutils.robot.THEN
import com.aetherinsight.goldentomatoes.testutils.robot.WHEN
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

        private lateinit var favoriteMovieUseCase: FavoriteMovieUseCase

        override fun setup() {
            MockKAnnotations.init(this, relaxUnitFun = true)

            favoriteMovieUseCase =
                FavoriteMovieUseCase(favoriteRepository)

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
            favoriteMovieUseCase.invoke(
                dumbMovieDetails1
            )
        }

        suspend fun invokeUseCaseWithFavorite() {
            favoriteMovieUseCase.invoke(
                dumbMovieDetails2
            )
        }

        fun assertCallRemoveFavorite() {
            coVerify(exactly = 1) {
                favoriteRepository.removeFavoriteMovie(dumbMovieDetails2.id)
            }
            coVerify(exactly = 0) {
                favoriteRepository.addFavoriteMovie(dumbMovieDetails1)
            }
        }

        fun assertCallAddFavorite() {
            coVerify(exactly = 0) {
                favoriteRepository.removeFavoriteMovie(dumbMovieDetails2.id)
            }
            coVerify(exactly = 1) {
                favoriteRepository.addFavoriteMovie(dumbMovieDetails1)
            }
        }


        private val dumbMovieDetails1 =
            MovieGlobal(
                id = 1L,
                title = "Movie 1",
                description = "Description 1",
                posterPath = "/path/to/poster1.jpg",
                favorite = false,
                scheduled = false
            )

        private val dumbMovieDetails2 =
            MovieGlobal(
                id = 2L,
                title = "Movie 2",
                description = "Description 2",
                posterPath = "/path/to/poster2.jpg",
                favorite = true,
                scheduled = true
            )
    }
}