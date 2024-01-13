package com.example.android_taskmaz.domain.usecases

import com.example.android_taskmaz.common.utils.FailureStatus
import com.example.android_taskmaz.common.utils.Resource
import com.example.android_taskmaz.domain.models.Option
import com.example.android_taskmaz.domain.models.Property
import com.example.android_taskmaz.domain.repository.Repository
import kotlinx.coroutines.runBlocking
import kotlinx.coroutines.test.runTest
import org.junit.Before
import org.junit.Test
import org.mockito.Mock
import org.mockito.Mockito
import org.mockito.MockitoAnnotations
import org.junit.Assert.*

class GetOptionsUseCaseTest {

    @Mock
    private lateinit var mockRepository: Repository

    private lateinit var getOptionsUseCase: GetOptionsUseCase

    @Before
    fun setUp() {
        MockitoAnnotations.initMocks(this)
        getOptionsUseCase = GetOptionsUseCase(mockRepository)
    }

    @Test
    fun `invoke returns success with data when repository succeeds`() = runTest {
        val groupId = 1
        val mockOptions = listOf(
            Property(1, "Property1", listOf(Option(1, "Option1"))),
            Property(2, "Property2", listOf(Option(2, "Option2")))
        )
        Mockito.`when`(mockRepository.getOptions(groupId)).thenReturn(Resource.Success(mockOptions))

        val result = getOptionsUseCase(groupId)

        Mockito.verify(mockRepository).getOptions(groupId)
        assertTrue(result is Resource.Success && result.data == mockOptions)
    }

    @Test
    fun `invoke returns failure when repository fails`() = runTest {
        val groupId = 1
        Mockito.`when`(mockRepository.getOptions(groupId)).thenReturn(Resource.Failure(FailureStatus.API_FAIL))

        val result = getOptionsUseCase(groupId)

        Mockito.verify(mockRepository).getOptions(groupId)
        assertTrue(result is Resource.Failure)
    }

    @Test(expected = Exception::class)
    fun `invoke throws exception when repository encounters an error`() = runTest {
        val groupId = 1
        val exception = RuntimeException("Error")
        Mockito.`when`(mockRepository.getOptions(groupId)).thenThrow(exception)

        getOptionsUseCase(groupId)

        Mockito.verify(mockRepository).getOptions(groupId)
    }
}
