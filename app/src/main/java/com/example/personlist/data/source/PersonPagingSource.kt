package com.example.personlist.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import java.io.IOException

class PersonPagingSource(
    private val dataSource: DataSource,
    private val completionHandler: FetchCompletionHandler //todo: kaldırılabilir?
) : PagingSource<Int, Person>() {

    private var response: FetchResponse? = null //todo: bunlar load'ın içine mi alınmalı?
    private var personList = mutableListOf<Person>()

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Person> {
        try {
            val nextPageNumber = params.key ?: 1
            dataSource.fetch(nextPageNumber.toString()) { fetchResponse, fetchError ->
                response = fetchResponse
            }
            personList.addAll(response?.people.orEmpty())
            val nextKey = if (personList.isEmpty()) {
                null
            } else {
                nextPageNumber + 1
            }

            return LoadResult.Page(
                data = personList,
                prevKey = if (nextPageNumber == 1) null else -1, //todo: null da olabilir?
                nextKey = nextKey
            )
        } catch (exception: IOException) {
            return LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Person>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}