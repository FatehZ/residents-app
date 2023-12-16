package com.ktxdevelopment.domain.usecase.local

import com.ktxdevelopment.data.local.model.ResidenceEntity
import com.ktxdevelopment.domain.repo.LocalRepository
import javax.inject.Inject

class SavePeopleUseCase @Inject constructor(private val localRepository: LocalRepository) {

    suspend fun execute(people: List<ResidenceEntity>) {
        localRepository.savePeople(people)
    }
}