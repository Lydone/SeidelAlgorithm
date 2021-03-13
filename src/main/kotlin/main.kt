fun main(args: Array<String>) {
    println(
        SeidelAlgorithm.calculateDistanceMatrix(
            arrayOf(
                intArrayOf(0, 1),
                intArrayOf(1, 0)
            )
        ).contentDeepToString()
    )
}