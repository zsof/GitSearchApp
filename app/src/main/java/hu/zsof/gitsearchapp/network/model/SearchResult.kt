package hu.zsof.gitsearchapp.network.model

data class SearchResult(
    val name: String = "",
    val desc: String = "",
    val starNumbers: Int = 0,
    val createDate: String = "",
    val lastUpdate: String = "",
    val ownerName: String = "",
    val ownerAvatar: String = "",
    val ownerLink: String = "",
    val repositoryLink: String = "",
    val forkNumbers: Int = 0,
)
