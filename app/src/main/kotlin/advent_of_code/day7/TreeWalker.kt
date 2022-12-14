package advent_of_code.day7

interface TreeWalker {
    fun visit(node: Node)
}

class NodeCounter : TreeWalker {
    var nodesVisited = 0

    override fun visit(node: Node) {
        nodesVisited++
    }
}

class TreePrettyPrinter : TreeWalker {
    val output = StringBuilder()

    override fun visit(node: Node) {
        output.append(node.toString() + "\n")
    }
}

class SmallestDirFinder(private val floor: Int) : TreeWalker {
    val smallestDirSize
        get() = smallDirs.minOf { it.contentsSize }

    private var smallDirs = mutableListOf<DirNode>()

    override fun visit(node: Node) {
        if (node is DirNode) {
            if (node.contentsSize >= floor) {
                smallDirs.add(node)
            }
        }
    }

}

class SmallDirFinder() : TreeWalker {
    var smallDirs = mutableListOf<DirNode>()

    override fun visit(node: Node) {
        if (node is DirNode) {
            if (node.contentsSize <= 100000) {
                smallDirs.add(node)
            }
        }
    }
}