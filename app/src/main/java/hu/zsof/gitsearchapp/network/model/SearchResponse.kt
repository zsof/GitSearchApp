package hu.zsof.gitsearchapp.network.model

import com.squareup.moshi.Json

data class SearchResponse(
    @field:Json(name = "total_count")
    val totalCount: Int,
    val items: List<ProjectData>,
)

data class ProjectData(
    @field:Json(name = "name")
    val repositoryName: String? = null,
    val owner: OwnerData = OwnerData(),
    @field:Json(name = "description")
    val desc: String? = null,
    @field:Json(name = "html_url")
    val projectLink: String? = null,
    @field:Json(name = "created_at")
    val createDate: String? = null,
    @field:Json(name = "updated_at")
    val updateDate: String? = null,
    @field:Json(name = "forks_count")
    val forkNumber: Int? = null,
    @field:Json(name = "stargazers_count")
    val starNumber: Int? = null,
)

data class OwnerData(
    @field:Json(name = "login")
    val ownerName: String? = null,
    @field:Json(name = "avatar_url")
    val avatarUrl: String? = null,
    @field:Json(name = "html_url")
    val ownerLink: String? = null,

)
