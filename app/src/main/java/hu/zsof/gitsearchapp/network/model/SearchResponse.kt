package hu.zsof.gitsearchapp.network.model

import com.squareup.moshi.Json

data class SearchResponse(
    @field:Json(name = "total_count")
    val totalCount: Int,
    val items: List<ProjectData>,
)

data class ProjectData(
    val name: String,
    val owner: OwnerData,
    @field:Json(name = "description")
    val desc: String,
    @field:Json(name = "url")
    val projectLink: String,
    @field:Json(name = "created_at")
    val createDate: String,
    @field:Json(name = "updated_at")
    val updateDate: String,
    @field:Json(name = "forks_count")
    val forkNumber: Int,
    @field:Json(name = "stargazers_count")
    val starNumber: Int,
)

data class OwnerData(
    @field:Json(name = "avatar_url")
    val avatarUrl: String,
    @field:Json(name = "url")
    val ownerLink: String,

)
