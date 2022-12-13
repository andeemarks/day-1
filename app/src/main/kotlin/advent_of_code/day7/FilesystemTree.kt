package advent_of_code.day7

class FilesystemTree {
    val root: Node = Node("/")
    var current: Node = this.root
        private set

    fun size(): Int {
        val visitor = NodeCounter()
        visitNode(root, visitor)

        return visitor.nodesVisited
    }

    private fun visitNode(node: Node, visitor: TreeWalker) {
        visitor.visit(node)
        val children = node.children
        children.forEach { visitNode(it, visitor) }
    }

    override fun toString(): String {
        val visitor = TreePrettyPrinter()
        visitNode(root, visitor)

        return visitor.output.toString()
    }

    fun findSmallDirs(): List<DirNode> {
        val smallDirFinder = SmallDirFinder()
        visitNode(root, smallDirFinder)

        return smallDirFinder.smallDirs
    }

    fun changeDirectory(cdCommand: CDCommand): Node {
        if (cdCommand.argument == CDCommand.ROOT) {
            current = root
            return current
        }

        if (cdCommand.argument == CDCommand.PARENT) {
            current = current.parent!!
        } else {
            val dir = DirNode(cdCommand.argument, current.level + 1, current)
            current.children.add(dir)
            current = dir
        }

        return current
    }

    fun addFilesToCurrentDirectory(contents: LSResult) {
        for (i in 0 until contents.size) {
            val newFile = FileNode(contents[i].name, current.level + 1, contents[i].size)
            current.add(newFile)
        }
    }
}