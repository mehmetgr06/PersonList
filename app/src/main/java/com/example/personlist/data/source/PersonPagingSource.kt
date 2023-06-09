package com.example.personlist.data.source

import androidx.paging.PagingSource
import androidx.paging.PagingState
import kotlin.coroutines.*

class PersonPagingSource(
    private val dataSource: DataSource,
    private val isRefreshing: Boolean = false,
    private val handleError: (FetchError) -> Unit
) : PagingSource<Int, Person>() {

    private var response: FetchResponse? = null

    override suspend fun load(params: LoadParams<Int>): LoadResult<Int, Person> {
        return try {
            val nextPageNumber = params.key ?: 1
            suspendCoroutine { continuation ->
                dataSource.fetch(if (isRefreshing) null else nextPageNumber.toString()) { fetchResponse, fetchError ->
                    response = fetchResponse
                    fetchError?.let {
                        handleError.invoke(fetchError)
                    }
                    continuation.resume(Unit)
                }
            }
            val newPersons = response?.people.orEmpty().distinctBy { it.id }
            LoadResult.Page(
                data = newPersons,
                prevKey = if (nextPageNumber == 1) null else -1,
                nextKey = response?.next?.toInt()
            )
        } catch (exception: Exception) {
            LoadResult.Error(exception)
        }
    }

    override fun getRefreshKey(state: PagingState<Int, Person>): Int? {
        return state.anchorPosition?.let { anchorPosition ->
            val anchorPage = state.closestPageToPosition(anchorPosition)
            anchorPage?.prevKey?.plus(1) ?: anchorPage?.nextKey?.minus(1)
        }
    }
}