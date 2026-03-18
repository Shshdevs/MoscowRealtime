package com.hotelka.moscowrealtime.data.mapper

import com.hotelka.moscowrealtime.data.dto.SearchResultDto
import com.hotelka.moscowrealtime.domain.model.SearchResult

fun SearchResultDto.toDomain(): SearchResult = SearchResult(
    searchResultForQuery = searchResultForQuery
)