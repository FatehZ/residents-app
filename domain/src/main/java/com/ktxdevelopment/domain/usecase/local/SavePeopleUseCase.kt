package com.ktxdevelopment.domain.usecase.local

import com.ktxdevelopment.domain.model.ResidentModel
import com.ktxdevelopment.domain.repo.LocalRepository
import javax.inject.Inject

class SavePeopleUseCase @Inject constructor(private val localRepository: LocalRepository) {

    suspend operator fun invoke(people: List<ResidentModel>) {
        localRepository.clearPeople()
        localRepository.savePeople(people)
    }
}