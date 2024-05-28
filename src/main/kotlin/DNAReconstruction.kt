package pl.bioinf

import pl.bioinf.data.NodesList
import java.util.PriorityQueue

class DNAReconstruction {
    fun reconstructDNA(list: NodesList, kNum: Int): String {
        if (list.nodes.isEmpty()) return ""

        val reconstructed = StringBuilder(list.first)

        var currentNode = list.nodes.entries.elementAt(0)
        while (true) {
            val currentNodeVis = list.nodes[currentNode.key]!!.second
            list.nodes[currentNode.key] = list.nodes[currentNode.key]!!.copy(second = currentNodeVis - 1)

            println("Current Node: ${currentNode.key}")

            // Find the next node with an edge of weight 1(2, 3) and not visited
            val nextNode = getNextNode(list, currentNode)

            if (nextNode == null) {
                // If no next node with edge of weight 1(2, 3) and not visited,
                // find shortest path to a non-visited node
                val (newNode, path) = findClosestUnvisitedNode(list.nodes, currentNode)
                if (newNode == null) break // No such node found, end reconstruction

                println("Path found to node not visited: ${path.joinToString(" -> ") { it }}")

                // Append the path to the reconstructed sequence
                for (step in path) {
                    reconstructed.append(currentNode.key.dropOverlap(step))
                    currentNode = list.nodes.entries.first { it.key == step }
                }
                continue
            }

            println("Next Node with edge of weight ${currentNode.value.first[nextNode.key]}: ${nextNode.key}")

            // Append the next node's value to the reconstructed sequence
            reconstructed.append(currentNode.key.dropOverlap(nextNode.key))

            currentNode = nextNode
        }

        return reconstructed.toString()
    }

    private fun getNextNode(
        list: NodesList,
        currentNode:  MutableMap. MutableEntry<String, Pair<MutableMap<String, Int>, Int>>,
        edgeWeight: Int = 1
    ):  MutableMap. MutableEntry<String, Pair<MutableMap<String, Int>, Int>>? {
        if (edgeWeight > 3) {
            return null
        }
        return currentNode.value.first.entries.asSequence()
            .filter { it.value == edgeWeight && list.nodes[it.key]!!.second > 0 }
            .map { entry -> list.nodes.entries.first { it.key == entry.key } }
            .firstOrNull() ?: getNextNode(list, currentNode, edgeWeight + 1)
    }

    private fun findClosestUnvisitedNode(
        nodes: MutableMap<String, Pair<MutableMap<String, Int>, Int>>,
        startNode: MutableMap.MutableEntry<String, Pair<MutableMap<String, Int>, Int>>
    ): Pair<String?, List<String>> {
        val distances = mutableMapOf<String, Int>().withDefault { Int.MAX_VALUE }
        val previousNodes = mutableMapOf<String, String>()
        val priorityQueue = PriorityQueue<Pair<String, Int>>(compareBy { it.second })

        distances[startNode.key] = 0
        priorityQueue.add(startNode.key to 0)

        while (priorityQueue.isNotEmpty()) {
            val (currentNodeKey, currentDistance) = priorityQueue.poll()
            val currentNode = nodes[currentNodeKey] ?: continue

            // Find the first unvisited node
            if (currentNode.second > 0) {
                val path = mutableListOf<String>()
                var stepNodeKey = currentNodeKey
                while (stepNodeKey != startNode.key) {
                    path.add(0, stepNodeKey)
                    stepNodeKey = previousNodes[stepNodeKey] ?: break
                }
                path.add(0, startNode.key)
                return currentNodeKey to path
            }

            currentNode.first.forEach { (neighborKey, weight) ->
                val neighbor = nodes[neighborKey]
                if (neighbor != null && neighbor.second > 0) {
                    val newDist = currentDistance + weight
                    if (newDist < distances.getValue(neighborKey)) {
                        distances[neighborKey] = newDist
                        previousNodes[neighborKey] = currentNodeKey
                        priorityQueue.add(neighborKey to newDist)
                    }
                }
            }
        }

        return null to emptyList()
    }
}
