package com.ktxdevelopment.domain.usecase.local

import com.ktxdevelopment.data.local.model.ResidentEntity
import com.ktxdevelopment.domain.repo.LocalRepository
import javax.inject.Inject

class SavePeopleUseCase @Inject constructor(private val localRepository: LocalRepository) {

    suspend fun execute(people: List<ResidentEntity>) {
        localRepository.savePeople(people)
    }
}