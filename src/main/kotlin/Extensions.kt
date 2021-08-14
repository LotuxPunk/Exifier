enum class Extensions {
    JPEG,
    PNG,
    JPG;

    companion object {
        val lowerValues = values().map { it.name.lowercase() }
    }
}