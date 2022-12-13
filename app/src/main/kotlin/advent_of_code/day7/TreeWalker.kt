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

class NodePrinter : TreeWalker {
    override fun visit(node: Node) {
        println(node.toString())
    }
}

class SmallDirFinder : TreeWalker {
    var smallDirs = mutableListOf<DirNode>()

    override fun visit(node: Node) {
        if (node is DirNode) {
            if (node.contentsSize <= 100000) {
                smallDirs.add(node)
            }
        }
    }
}
