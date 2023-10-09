package me.segosambel.backstage.home

import androidx.arch.core.executor.testing.InstantTaskExecutorRule
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.flowOf
import kotlinx.coroutines.test.TestDispatcher
import kotlinx.coroutines.test.UnconfinedTestDispatcher
import kotlinx.coroutines.test.resetMain
import kotlinx.coroutines.test.runTest
import kotlinx.coroutines.test.setMain
import me.segosambel.backstage.core.data.Resource
import me.segosambel.backstage.core.domain.model.Movie
import me.segosambel.backstage.core.domain.usecase.GetMoviesUseCase
import me.segosambel.backstage.util.DummyData
import me.segosambel.backstage.util.getOrAwaitValue
import org.junit.After
import org.junit.Assert
import org.junit.Before
import org.junit.Rule
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.Mockito.`when`
import org.mockito.junit.MockitoJUnitRunner

@ExperimentalCoroutinesApi
@RunWith(MockitoJUnitRunner::class)
class HomeViewModelTest {

    @get:Rule
    val rule = InstantTaskExecutorRule()

    @Mock
    private lateinit var getMovieUseCase: GetMoviesUseCase
    private lateinit var viewModel: HomeViewModel
    private val dataDummy = DummyData.generateDummyNewsEntity()

    @Before
    fun setUp() {
        viewModel = HomeViewModel(getMovieUseCase)
    }

    private val testDispatcher: TestDispatcher = UnconfinedTestDispatcher()

    @Before
    fun setupDispatcher() {
        Dispatchers.setMain(testDispatcher)
    }

    @After
    fun tearDownDispatcher() {
        Dispatchers.resetMain()
    }

    @Test
    fun testFetchMovieSuccess() = runTest {
        val successResult = flowOf(Resource.Success(dataDummy))
        `when`(getMovieUseCase.invoke()).thenReturn(successResult)

        val actualValue = viewModel.movies.getOrAwaitValue()
        Mockito.verify(getMovieUseCase).invoke()
        Assert.assertNotNull(actualValue)
        Assert.assertTrue(actualValue is Resource.Success)
        Assert.assertEquals(dataDummy.size, (actualValue as Resource.Success).data?.size)
    }

    @Test
    fun testFetchMovieError() {
        val errorResult = flowOf(Resource.Error<List<Movie>>("ERROR TEST"))
        `when`(getMovieUseCase.invoke()).thenReturn(errorResult)

        val actualValue = viewModel.movies.getOrAwaitValue()
        Assert.assertNotNull(actualValue)
        Assert.assertTrue(actualValue is Resource.Error)
        Assert.assertEquals((actualValue as Resource.Error).message, "ERROR TEST")
    }
}