package advent_of_code.day7

interface NodeVisitor {
    fun visit(node: Node)
}

class NodeCounter : NodeVisitor {
    var nodesVisited = 0
    override fun visit(node: Node) {
        nodesVisited++
    }
}

class NodePrinter : NodeVisitor {
    override fun visit(node: Node) {
        println(node.toString())
    }
}

class SmallDirFinder : NodeVisitor {
    var smallDirs = mutableListOf<DirNode>()

    override fun visit(node: Node) {
        if (node is DirNode) {
            if (node.contentsSize <= 100000) {
                smallDirs.add(node)
            }
        }
    }
}
