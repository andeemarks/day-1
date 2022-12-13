package advent_of_code.day7

import advent_of_code.LSResult

class DirTree {
    fun size(): Int {
        val visitor = NodeCounter()
        visitNode(root, visitor)

        return visitor.nodesVisited
    }

    private fun visitNode(node: Node, visitor: NodeVisitor) {
        visitor.visit(node)
        val children = node.children
        children.forEach { visitNode(it, visitor) }
    }

    fun show() {
        visitNode(root, NodePrinter())
    }

    fun findSmallDirs(): List<DirNode> {
        val smallDirFinder = SmallDirFinder()
        visitNode(root, smallDirFinder)

        return smallDirFinder.smallDirs
    }

    fun pushDirectory(cdCommand: CDCommand): Node {

        if (cdCommand.argument == CDCommand.ROOT) {
            current = root
            return current
        }

        if (cdCommand.argument == CDCommand.PARENT) {
            current = current.parent!!
        } else {
            val dir = DirNode(cdCommand.argument, current.level + 1)
            dir.parent = current
            current.children.add(dir)
            current = dir
        }

        return current

    }

    fun pushDirectoryContents(contents: LSResult) {
        val currentNode = current
        for (i: Int in 0 until contents.size) {
            currentNode.children.add(FileNode(contents[i].name, currentNode.level + 1, contents[i].size))
            currentNode.increaseContentSize(contents[i].size)
        }
    }

    val root: Node = Node("/")
    var current: Node = this.root
}