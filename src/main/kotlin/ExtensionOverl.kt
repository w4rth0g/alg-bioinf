package pl.bioinf

fun String.dropOverlap(other: String): String {
    fun longestOverlap(a: String, b: String): Int {
        var maxOverlap = 0
        for (i in 1..minOf(a.length, b.length)) {
            if (a.take(i) == b.takeLast(i)) {
                maxOverlap = i
            }
            if (a.takeLast(i) == b.take(i)) {
                maxOverlap = i
            }
        }
        return maxOverlap
    }

    // Find the longest overlap in all four scenarios
    val overlap1 = longestOverlap(this, other) // prefix with suffix, suffix with prefix
    val overlap2 = longestOverlap(this.reversed(), other.reversed()) // suffix with suffix, prefix with prefix

    // Remove the longest overlap from the second string
    val longestOverlap = maxOf(overlap1, overlap2)

    return when {
        this.take(longestOverlap) == other.takeLast(longestOverlap) -> other.dropLast(longestOverlap)
        this.takeLast(longestOverlap) == other.take(longestOverlap) -> other.drop(longestOverlap)
        this.take(longestOverlap) == other.take(longestOverlap) -> other.drop(longestOverlap)
        this.takeLast(longestOverlap) == other.takeLast(longestOverlap) -> other.dropLast(longestOverlap)
        else -> other
    }
}