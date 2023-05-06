package com.example.personlist.domain

import com.example.personlist.data.repository.PersonRepository
import com.example.personlist.data.source.FetchError
import javax.inject.Inject

class GetPersonsUseCase @Inject constructor(private val personRepository: PersonRepository) {

    operator fun invoke(isRefreshing: Boolean = false, handleError: (FetchError) -> Unit) =
        personRepository.getPersonList(isRefreshing, handleError)
}