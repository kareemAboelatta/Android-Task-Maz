package com.example.android_taskmaz.domain.usecases

import com.example.android_taskmaz.common.utils.FailureStatus
import kotlinx.coroutines.test.runTest
import com.example.android_taskmaz.common.utils.Resource
import com.example.android_taskmaz.domain.models.Category
import com.example.android_taskmaz.domain.models.SubCategory
import com.example.android_taskmaz.domain.repository.Repository
import kotlinx.coroutines.runBlocking
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.junit.Assert.*

class GetCategoriesUseCaseTest {

    @Mock
    private lateinit var mockRepository: Repository

    private lateinit var getCategoriesUseCase: GetCategoriesUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getCategoriesUseCase = GetCategoriesUseCase(mockRepository)
    }

    @Test
    fun `invoke returns success with data when repository succeeds`() = runTest {
        val mockCategories = listOf(
            Category(1, "Category1", listOf(SubCategory(1, "SubCategory1"))),
            Category(2, "Category2", listOf(SubCategory(2, "SubCategory2")))
        )
        Mockito.`when`(mockRepository.getCategories()).thenReturn(Resource.Success(mockCategories))

        val result = getCategoriesUseCase()

        Mockito.verify(mockRepository).getCategories()
        assertTrue(result is Resource.Success && result.data == mockCategories)
    }

    @Test
    fun `invoke returns failure when repository fails`() = runTest {
        Mockito.`when`(mockRepository.getCategories()).thenReturn(Resource.Failure(FailureStatus.API_FAIL))

        val result = getCategoriesUseCase()

        Mockito.verify(mockRepository).getCategories()
        assertTrue(result is Resource.Failure)
    }

    @Test(expected = Exception::class)
    fun `invoke throws exception when repository encounters an error`() = runTest {
        val exception = RuntimeException("Error")
        Mockito.`when`(mockRepository.getCategories()).thenThrow(exception)

        getCategoriesUseCase()

        Mockito.verify(mockRepository).getCategories()
    }
}
