package com.odisby.goldentomatoes.feature.movielist.ui

import com.odisby.goldentomatoes.core.network.model.Resource
import com.odisby.goldentomatoes.core.ui.constants.ListTypes
import com.odisby.goldentomatoes.feature.movielist.data.GetListMoviesUseCase
import com.odisby.goldentomatoes.feature.movielist.model.MovieListItem
import com.odisby.goldentomatoes.testutils.robot.BaseRobot
import com.odisby.goldentomatoes.testutils.robot.GIVEN
import com.odisby.goldentomatoes.testutils.robot.RUN_UNIT_TEST
import com.odisby.goldentomatoes.testutils.robot.THEN
import com.odisby.goldentomatoes.testutils.robot.WHEN
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.flow.flowOf
import org.junit.After
import org.junit.Before
import org.junit.Test

class MovieListViewModelTest {

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
    fun `getDiscoverMovies in a success case should update moviesList`() {
        RUN_UNIT_TEST(robot) {
            GIVEN { getDiscoverMoviesUseCaseSuccess() }
            WHEN { invokeUseCase(ListTypes.DISCOVER) }
            THEN { assertMoviesListIsUpdated() }
        }
    }

    @Test
    fun `getDiscoverMovies in a error case should update errorMessage`() {
        RUN_UNIT_TEST(robot) {
            GIVEN { getDiscoverMoviesUseCaseError() }
            WHEN { invokeUseCase(ListTypes.DISCOVER) }
            THEN { assertErrorMessageContainsError() }
        }
    }


    class Robot : BaseRobot {

        companion object {
            const val ERROR_MESSAGE = "Error for tests"
        }

        @MockK
        lateinit var getListMoviesUseCase: GetListMoviesUseCase

        private lateinit var movieListViewModel: MovieListViewModel

        override fun setup() {
            MockKAnnotations.init(this, relaxUnitFun = true)
            movieListViewModel = MovieListViewModel(getListMoviesUseCase)
        }

        override fun tearsDown() {

        }

        fun invokeUseCase(listTypes: ListTypes) {
            movieListViewModel.getDiscoverMovies(listTypes)
        }

        fun getDiscoverMoviesUseCaseSuccess() {
            coEvery {
                getListMoviesUseCase.invoke(ListTypes.DISCOVER)
            } returns flowOf(Resource.Success(dumbDiscoverMovieList))
        }

        fun getDiscoverMoviesUseCaseError() {
            coEvery {
                getListMoviesUseCase.invoke(ListTypes.DISCOVER)
            } returns flowOf(Resource.Error(ERROR_MESSAGE))
        }

        fun assertMoviesListIsUpdated() {
            assert(movieListViewModel.state.value.moviesList == dumbDiscoverMovieList)
        }

        fun assertErrorMessageContainsError() {
            assert(movieListViewModel.state.value.errorMessage == ERROR_MESSAGE)
        }


        val dumbDiscoverMovieList = listOf(
            MovieListItem(
                id = 1L,
                title = "Discover Movie 1",
                posterPath = "/path/to/discover/poster1.jpg"
            ),
            MovieListItem(
                id = 2L,
                title = "Discover Movie 2",
                posterPath = "/path/to/discover/poster2.jpg"
            ),
            MovieListItem(
                id = 3L,
                title = "Discover Movie 3",
                posterPath = "/path/to/discover/poster3.jpg"
            )
        )

        val dumbFavoritesMovieList = listOf(
            MovieListItem(
                id = 101L,
                title = "Favorite Movie 1",
                posterPath = "/path/to/favorites/poster1.jpg"
            ),
            MovieListItem(
                id = 102L,
                title = "Favorite Movie 2",
                posterPath = "/path/to/favorites/poster2.jpg"
            ),
            MovieListItem(
                id = 103L,
                title = "Favorite Movie 3",
                posterPath = "/path/to/favorites/poster3.jpg"
            )
        )
    }
}