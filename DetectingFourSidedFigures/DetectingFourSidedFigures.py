from functools import reduce

class Node:
    def __init__(self, x, y):
        self.x = x
        self.y = y
        self.down = None
        self.right = None

    def dirNodes(self, dirFunc):
        if dirFunc(self) is None:
            return []

        l = dirFunc(self).dirNodes(dirFunc)
        l.append(dirFunc(self))
        return l

def getNodeAt(x, y):
    if (x, y) not in nodes:
        nodes[(x, y)] = Node(x, y)
    return nodes[(x, y)]

def readInput():
    with open('input2.txt') as f:
        return f.readlines()

def parseDirection(lines, direction, connectorChar, isTranspose):
    for row, line in enumerate(lines):
        currentMode = 0
        currentNode = None
        for column, c in enumerate(line):
            coordParam = (row, column) if not isTranspose else (column, row)
            if c == '+' and currentMode == 0:
                currentMode = 1
                currentNode = getNodeAt(*coordParam)
            elif c == '+' and currentMode == 1:
                currentNode.__dict__[direction] = getNodeAt(*coordParam)
                currentNode = currentNode.__dict__[direction]
            elif c != connectorChar:
                currentMode = 0
                currentNode = None

def transpose(things):
    return zip(*things)

nodes = {}
if __name__ == "__main__":
    lines = readInput()

    parseDirection(lines, 'right', '-', False)
    parseDirection(transpose(lines), 'down', '|', True)

    counter = 0
    for coordinate in nodes:
        node = nodes[coordinate]
        rightNodes = node.dirNodes(lambda x : x.right)
        downRightNodes = sum(map(lambda x : x.dirNodes(lambda y : y.down), rightNodes), [])
        downNodes = node.dirNodes(lambda x : x.down)
        rightDownNodes = sum(map(lambda x : x.dirNodes(lambda y : y.right), downNodes), [])
        counter += len(set(rightDownNodes).intersection(set(downRightNodes)))

    print(counter)