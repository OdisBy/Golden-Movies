package com.aetherinsight.goldentomatoes.feature.home.ui

import com.aetherinsight.goldentomatoes.core.network.model.Resource
import com.aetherinsight.goldentomatoes.feature.home.data.GetDiscoverMoviesUseCase
import com.aetherinsight.goldentomatoes.feature.home.data.GetFavoriteMoviesUseCase
import com.aetherinsight.goldentomatoes.feature.home.data.SearchMoviesUseCase
import com.aetherinsight.goldentomatoes.feature.home.model.HomeMovie
import com.aetherinsight.goldentomatoes.feature.home.model.SearchMovie
import com.aetherinsight.goldentomatoes.feature.home.ui.HomeViewModelTest.Robot.Companion.RUN_SEARCH_QUERY_1
import com.aetherinsight.goldentomatoes.feature.home.ui.HomeViewModelTest.Robot.Companion.RUN_SEARCH_QUERY_2
import com.aetherinsight.goldentomatoes.feature.home.ui.HomeViewModelTest.Robot.Companion.RUN_SEARCH_QUERY_3
import com.aetherinsight.goldentomatoes.feature.home.ui.HomeViewModelTest.Robot.Companion.RUN_SEARCH_QUERY_5
import com.aetherinsight.goldentomatoes.testutils.MainDispatcherRule
import com.aetherinsight.goldentomatoes.testutils.robot.AND
import com.aetherinsight.goldentomatoes.testutils.robot.BaseRobot
import com.aetherinsight.goldentomatoes.testutils.robot.GIVEN
import com.aetherinsight.goldentomatoes.testutils.robot.RUN_UNIT_TEST
import com.aetherinsight.goldentomatoes.testutils.robot.THEN
import com.aetherinsight.goldentomatoes.testutils.robot.WHEN
import io.kotest.matchers.shouldBe
import io.mockk.MockKAnnotations
import io.mockk.coEvery
import io.mockk.coVerify
import io.mockk.impl.annotations.MockK
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.advanceTimeBy
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

    @Test
    fun `UiState State after DiscoverMovies Success should have a isLoadingDiscover false and a list on discoverList`() =
        runTest {
            RUN_UNIT_TEST(robot) {
                GIVEN { getDiscoverMoviesUseCaseSuccess() }
                WHEN { callGetDiscoverMovies() }
                advanceUntilIdle()
                THEN { getDiscoverMoviesUiStateSuccess() }
            }
        }

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

    @Test
    fun `UiState State after FavoriteMovies Error should have isLoadingFavorite false and a empty list on favoriteList`() =
        runTest {
            RUN_UNIT_TEST(robot) {
                GIVEN { getFavoriteMoviesUseCaseEmptyList() }
                WHEN {
                    callGetFavoriteMovies()
                    advanceUntilIdle()
                }
                THEN { getFavoriteMoviesUiStateEmptyList() }
            }
        }

    @Test
    fun `When input text change should call runSearch after 500ms`() = runTest {
        RUN_UNIT_TEST(robot) {
            GIVEN { getSearchMoviesUseCaseSuccess() }
            WHEN { inputTextUpdate(RUN_SEARCH_QUERY_1) }
            AND { advanceTimeBy(501) }
            THEN { shouldHaveCalledRunSearch(RUN_SEARCH_QUERY_1, 1) }
        }
    }

    @Test
    fun `Run Search should be called just to last query that do not change on last 500ms`() =
        runTest {
            RUN_UNIT_TEST(robot) {
                GIVEN { getSearchMoviesUseCaseSuccess() }
                WHEN {
                    inputTextUpdate(RUN_SEARCH_QUERY_1)
                    advanceTimeBy(499)
                    inputTextUpdate(RUN_SEARCH_QUERY_2)
                    advanceTimeBy(499)
                    inputTextUpdate(RUN_SEARCH_QUERY_3)
                    advanceTimeBy(501)
                }
                THEN {
                    shouldHaveCalledRunSearch(RUN_SEARCH_QUERY_1, 0)
                    shouldHaveCalledRunSearch(RUN_SEARCH_QUERY_2, 0)
                    shouldHaveCalledRunSearch(RUN_SEARCH_QUERY_3, 1)
                }
            }
        }

    @Test
    fun `Run Search should be called only if input text is biggest than 3 chars`() = runTest {
        RUN_UNIT_TEST(robot) {
            WHEN {
                inputTextUpdate(RUN_SEARCH_QUERY_5)
                advanceTimeBy(501)
            }
            THEN {
                getSearchMoviesUiStateEmpty()
            }
        }
    }

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

        @MockK
        lateinit var searchMoviesUseCase: SearchMoviesUseCase

        private lateinit var homeViewModel: HomeViewModel


        override fun setup() {
            MockKAnnotations.init(this, relaxUnitFun = true)

            homeViewModel =
                HomeViewModel(
                    getDiscoverMoviesUseCase,
                    getFavoriteMoviesUseCase,
                    searchMoviesUseCase
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

        fun getSearchMoviesUseCaseSuccess(query: String? = null) {
            if (query != null) {
                coEvery {
                    searchMoviesUseCase.invoke(query)
                } returns flowOf(dummySearchMovies.filter { it.title.contains(query) })
                return
            }

            coEvery {
                searchMoviesUseCase.invoke(any())
            } returns flowOf(dummySearchMovies)

        }

        fun inputTextUpdate(query: String) {
            homeViewModel.updateInput(query)
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

        fun getSearchMoviesUiStateSuccess(query: String) {
            homeViewModel.state.value.searchMovieList shouldBe dummySearchMovies.filter {
                it.title.contains(
                    query
                )
            }
            homeViewModel.state.value.queryHasNoResults shouldBe false
            homeViewModel.state.value.isSearching shouldBe false
            homeViewModel.state.value.searchErrorMessage shouldBe null
            homeViewModel.state.value.searchQuery shouldBe query
        }

        fun getSearchMoviesUiStateSuccessButEmpty(query: String) {
            homeViewModel.state.value.searchMovieList shouldBe emptyList()
            homeViewModel.state.value.searchErrorMessage shouldBe null
            homeViewModel.state.value.searchQuery shouldBe query
            homeViewModel.state.value.queryHasNoResults shouldBe true
            homeViewModel.state.value.isSearching shouldBe false
        }

        fun getSearchMoviesUiStateEmpty() {
            homeViewModel.state.value.searchMovieList shouldBe emptyList()
            homeViewModel.state.value.queryHasNoResults shouldBe false
            homeViewModel.state.value.isSearching shouldBe false
            homeViewModel.state.value.searchErrorMessage shouldBe null
        }

        fun runSearch(query: String) {
            homeViewModel.runSearch(query)
        }

        fun shouldHaveCalledRunSearch(query: String, times: Int) {
            coVerify(exactly = times) { homeViewModel.runSearch(query) }
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