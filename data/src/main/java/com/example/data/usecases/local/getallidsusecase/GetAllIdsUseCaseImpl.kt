package com.example.data.usecases.local.getallidsusecase

import com.example.core.repository.local.LocalRepository
import com.example.core.repository.usecases.local.getallidsusecase.GetAllIdsUseCase
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class GetAllIdsUseCaseImpl @Inject constructor(
    private val localRepository: LocalRepository
): GetAllIdsUseCase{
    override fun invoke(): Flow<List<Int>> = localRepository.getAllIds()
}
