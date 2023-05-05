package com.example.personlist.data.repository

import androidx.paging.Pager
import androidx.paging.PagingConfig
import androidx.paging.PagingData
import com.example.personlist.data.source.DataSource
import com.example.personlist.data.source.Person
import com.example.personlist.data.source.PersonPagingSource
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PersonRepository @Inject constructor(
    private val dataSource: DataSource
) {

    fun getPersonList(): Flow<PagingData<Person>> {
        return Pager(
            config = PagingConfig(
                pageSize = 20
            ),
            pagingSourceFactory = { PersonPagingSource(dataSource) }
        ).flow
    }
}