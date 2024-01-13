package com.example.android_taskmaz.domain.usecases

import com.example.android_taskmaz.common.utils.FailureStatus
import com.example.android_taskmaz.domain.models.Property
import com.example.android_taskmaz.domain.models.Option
import com.example.android_taskmaz.domain.repository.Repository
import com.example.android_taskmaz.common.utils.Resource
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.junit.Assert.*

class GetPropertiesUseCaseTest {

    @Mock
    private lateinit var mockRepository: Repository

    private lateinit var getPropertiesUseCase: GetPropertiesUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.openMocks(this)
        getPropertiesUseCase = GetPropertiesUseCase(mockRepository)
    }

    @Test
    fun `invoke returns success with data when repository succeeds`() = runTest {
        val categoryId = 1
        val mockOptions = listOf(
            Option(1, "Option1"),
            Option(2, "Option2")
        )
        val mockProperties = listOf(
            Property(1, "Property1", mockOptions),
            Property(2, "Property2", mockOptions)
        )
        val expectedResult = Resource.Success(mockProperties)

        Mockito.`when`(mockRepository.getProperties(categoryId)).thenReturn(expectedResult)

        val result = getPropertiesUseCase(categoryId)

        Mockito.verify(mockRepository).getProperties(categoryId)
        assertEquals(expectedResult, result)
    }

    @Test
    fun `invoke returns failure when repository fails`() = runTest {
        val categoryId = 1
        val failureResult = Resource.Failure(
            status = FailureStatus.API_FAIL,
            errorCode = 500,
            errorMessage = "Internal Server Error"
        )

        Mockito.`when`(mockRepository.getProperties(categoryId)).thenReturn(failureResult)

        val result = getPropertiesUseCase(categoryId)

        Mockito.verify(mockRepository).getProperties(categoryId)
        assertEquals(failureResult, result)
    }

    @Test(expected = Exception::class)
    fun `invoke throws exception when repository encounters unexpected error`() = runTest {
        val categoryId = 1
        val exception = RuntimeException("Unexpected Error")
        Mockito.`when`(mockRepository.getProperties(categoryId)).thenThrow(exception)

        getPropertiesUseCase(categoryId)

        Mockito.verify(mockRepository).getProperties(categoryId)
    }
}
