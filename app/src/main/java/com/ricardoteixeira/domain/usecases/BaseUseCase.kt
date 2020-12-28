package com.ricardoteixeira.domain.usecases

interface BaseUseCase<in Parameter, out Result> {

    suspend operator fun invoke(params: Parameter): Result
}