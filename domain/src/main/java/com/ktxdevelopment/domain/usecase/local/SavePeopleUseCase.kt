package com.ktxdevelopment.domain.usecase.local

import com.ktxdevelopment.domain.model.ResidentModel
import com.ktxdevelopment.domain.repo.LocalRepository
import javax.inject.Inject

class SavePeopleUseCase @Inject constructor(private val localRepository: LocalRepository) {

    suspend fun execute(people: List<ResidentModel>) {
        localRepository.savePeople(people)
    }
}