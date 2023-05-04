package com.example.personlist.domain

import com.example.personlist.data.repository.PersonRepository
import javax.inject.Inject

class GetPersonsUseCase @Inject constructor(private val personRepository: PersonRepository) {

    operator fun invoke() = personRepository.getPersonList()
}