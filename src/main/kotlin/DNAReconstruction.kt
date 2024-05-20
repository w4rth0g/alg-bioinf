package pl.bioinf

import pl.bioinf.data.Node
import pl.bioinf.data.NodesList
import java.util.PriorityQueue
import java.util.UUID

class DNAReconstruction {
    fun reconstructDNA(nodes: NodesList): String {
        if (nodes.allNodes.isEmpty()) return ""

        // Start with the first node
        val reconstructed = StringBuilder(nodes.firstNode.value)

        // Keep track of visited nodes
        val visited = mutableSetOf(nodes.firstNode.id)

        var currentNode = nodes.firstNode
        while (true) {
            // Find the next node with an edge of weight 1
            val nextNode = currentNode.nexts.entries.asSequence()
                .filter { it.value == 1 && it.key !in visited }
                .map { entry -> nodes.allNodes.first { it.id == entry.key } }
                .firstOrNull()

            if (nextNode == null) {
                // If no next node with edge of weight 1, find shortest path to a node with such edges
                val (newNode, path) = findClosestNodeWithEdgeOfWeight1(nodes.allNodes, currentNode, visited)
                if (newNode == null) break // No such node found, end reconstruction

                // Append the path to the reconstructed sequence
                for (step in path) {
                    val overlap = currentNode.nexts[step.id]!!
                    reconstructed.append(step.value.drop(overlap))
                    visited.add(step.id)
                    currentNode = step
                }
                continue
            }

            // Append the next node's value to the reconstructed sequence
            val overlap = currentNode.nexts[nextNode.id]!!
            reconstructed.append(nextNode.value.drop(overlap))

            // Mark the node as visited
            visited.add(nextNode.id)
            currentNode = nextNode
        }

        return reconstructed.toString()
    }

    private fun findClosestNodeWithEdgeOfWeight1(
        nodes: List<Node>,
        startNode: Node,
        visited: Set<UUID>
    ): Pair<Node?, List<Node>> {
        val distances = mutableMapOf<UUID, Int>().withDefault { Int.MAX_VALUE }
        val previousNodes = mutableMapOf<UUID, Node>()
        val priorityQueue = PriorityQueue<Pair<Node, Int>>(compareBy { it.second })

        distances[startNode.id] = 0
        priorityQueue.add(startNode to 0)

        while (priorityQueue.isNotEmpty()) {
            val (currentNode, currentDistance) = priorityQueue.poll()

            // Find the first node with an edge of weight 1
            if (currentNode.nexts.any { it.value == 1 && it.key !in visited }) {
                val path = mutableListOf<Node>()
                var stepNode = currentNode
                while (stepNode.id != startNode.id) {
                    path.add(0, stepNode)
                    stepNode = previousNodes[stepNode.id]!!
                }
                return currentNode to path
            }

            currentNode.nexts.forEach { (neighborId, weight) ->
                val neighbor = nodes.first { it.id == neighborId }
                if (neighborId !in visited) {
                    val newDist = currentDistance + weight
                    if (newDist < distances.getValue(neighborId)) {
                        distances[neighborId] = newDist
                        previousNodes[neighborId] = currentNode
                        priorityQueue.add(neighbor to newDist)
                    }
                }
            }
        }

        return null to emptyList()
    }
}
