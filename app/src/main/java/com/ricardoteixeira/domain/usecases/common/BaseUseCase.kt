package com.ricardoteixeira.domain.usecases.common

interface BaseUseCase<in Parameter, out Result> {

    suspend operator fun invoke(params: Parameter): Result
}