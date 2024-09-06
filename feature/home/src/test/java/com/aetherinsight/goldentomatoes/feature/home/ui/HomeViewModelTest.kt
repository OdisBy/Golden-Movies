package com.aetherinsight.goldentomatoes.feature.home.ui

import com.aetherinsight.goldentomatoes.core.data.model.SearchMovie
import com.aetherinsight.goldentomatoes.core.network.model.Resource
import com.aetherinsight.goldentomatoes.feature.home.data.GetDiscoverMoviesUseCase
import com.aetherinsight.goldentomatoes.feature.home.data.GetFavoriteMoviesUseCase
import com.aetherinsight.goldentomatoes.feature.home.model.HomeMovie
import com.aetherinsight.goldentomatoes.testutils.MainDispatcherRule
import com.aetherinsight.goldentomatoes.testutils.robot.BaseRobot
import com.aetherinsight.goldentomatoes.testutils.robot.GIVEN
import com.aetherinsight.goldentomatoes.testutils.robot.RUN_UNIT_TEST
import com.aetherinsight.goldentomatoes.testutils.robot.THEN
import com.aetherinsight.goldentomatoes.testutils.robot.WHEN
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceUntilIdle
import kotlinx.coroutines.test.runTest
import org.junit.After
import org.junit.Before
import org.junit.Rule
import org.junit.Test

@OptIn(ExperimentalCoroutinesApi::class)
class HomeViewModelTest {

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

//    @Test
//    fun `UiState State after DiscoverMovies Success should have a isLoadingDiscover false and a list on discoverList`() =
//        runTest {
//            RUN_UNIT_TEST(robot) {
//                GIVEN { getDiscoverMoviesUseCaseSuccess() }
//                WHEN { callGetDiscoverMovies() }
//                advanceUntilIdle()
//                THEN { getDiscoverMoviesUiStateSuccess() }
//            }
//        }

    @Test
    fun `UiState State after DiscoverMovies Error should have a isLoadingDiscover false and a empty list on discoverList`() =
        runTest {
            RUN_UNIT_TEST(robot) {
                GIVEN { getDiscoverMoviesUseCaseError() }
                WHEN { callGetDiscoverMovies() }
                advanceUntilIdle()
                THEN { getDiscoverMoviesUiStateError() }
            }
        }


    @Test
    fun `UiState State after FavoriteMovies Success should have isLoadingFavorite false and a list on favoriteList`() =
        runTest {
            RUN_UNIT_TEST(robot) {
                GIVEN { getFavoriteMoviesUseCaseWithData() }
                WHEN { callGetFavoriteMovies() }
                advanceUntilIdle()
                THEN { getFavoriteMoviesUiStateSuccess() }
            }
        }
//
//    @Test
//    fun `UiState State after FavoriteMovies Error should have isLoadingFavorite false and a empty list on favoriteList`() =
//        runTest {
//            RUN_UNIT_TEST(robot) {
//                GIVEN { getFavoriteMoviesUseCaseEmptyList() }
//                WHEN {
//                    callGetFavoriteMovies()
//                    advanceUntilIdle()
//                }
//                THEN { getFavoriteMoviesUiStateEmptyList() }
//            }
//        }

    class Robot : BaseRobot {

        companion object {
            const val RUN_SEARCH_QUERY_1 = "Meu M"
            const val RUN_SEARCH_QUERY_2 = "Inter"
            const val RUN_SEARCH_QUERY_3 = "Meu Malvado F"
            const val RUN_SEARCH_QUERY_4 = "NONE MOVIE WITH THIS TITLE"
            const val RUN_SEARCH_QUERY_5 = "Me"
        }

        @MockK
        lateinit var getDiscoverMoviesUseCase: GetDiscoverMoviesUseCase

        @MockK
        lateinit var getFavoriteMoviesUseCase: GetFavoriteMoviesUseCase

        private lateinit var homeViewModel: HomeViewModel


        override fun setup() {
            MockKAnnotations.init(this, relaxUnitFun = true)

            homeViewModel =
                HomeViewModel(
                    getDiscoverMoviesUseCase,
                    getFavoriteMoviesUseCase,
                )


            coEvery {
                getDiscoverMoviesUseCase.invoke()
            } returns flowOf(Resource.Success(emptyList()))
        }

        override fun tearsDown() {}


        fun getDiscoverMoviesUseCaseSuccess() {
            coEvery {
                getDiscoverMoviesUseCase.invoke()
            } returns flowOf(Resource.Success(dummyHomeMovies))
        }

        fun getDiscoverMoviesUseCaseError() {
            coEvery {
                getDiscoverMoviesUseCase.invoke()
            } returns flowOf(Resource.Error(""))
        }

        fun getFavoriteMoviesUseCaseWithData() {
            coEvery {
                getFavoriteMoviesUseCase.invoke()
            } returns flowOf(dummyHomeMovies)
        }

        fun getFavoriteMoviesUseCaseEmptyList() {
            coEvery {
                getFavoriteMoviesUseCase.invoke()
            } returns flowOf(emptyList())
        }

        fun callGetDiscoverMovies() {
            homeViewModel.getDiscoverMovies()
        }

        fun callGetFavoriteMovies() {
            homeViewModel.getFavoriteMovies()
        }

        fun getDiscoverMoviesUiStateSuccess() {
            homeViewModel.state.value.isLoadingDiscover shouldBe false
            homeViewModel.state.value.discoverList shouldBe dummyHomeMovies
        }

        fun getDiscoverMoviesUiStateError() {
            homeViewModel.state.value.isLoadingDiscover shouldBe false
            homeViewModel.state.value.discoverList shouldBe emptyList()
        }

        fun getFavoriteMoviesUiStateSuccess() {
            homeViewModel.state.value.isLoadingFavorite shouldBe false
            homeViewModel.state.value.favoriteList shouldBe dummyHomeMovies
        }

        fun getFavoriteMoviesUiStateEmptyList() {
            homeViewModel.state.value.isLoadingFavorite shouldBe false
            homeViewModel.state.value.favoriteList shouldBe emptyList()
        }


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
            SearchMovie(
                id = 8L,
                title = "Meu Malvado Favorito 4",
                favorite = true,
            )
        )
    }
}