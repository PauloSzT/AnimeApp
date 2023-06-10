package com.example.core.repository.usecases.local.getallidsusecase

import kotlinx.coroutines.flow.Flow

interface GetAllIdsUseCase {
    operator fun invoke(): Flow<List<Int>>
}
