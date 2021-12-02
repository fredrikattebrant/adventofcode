# AOC 2020 Day3, part 2
#
def isTree(map, row, col, wrap):
    wrappedCol = col % wrap
    return  map[row][wrappedCol] == '#'

def traverse(map, rowDelta, colDelta):
    r = 0
    c = 0
    treeCount = 0
    while r < rows - 1:
        r += rowDelta
        c += colDelta
        if isTree(baseMap, r, c, wrap):
            treeCount += 1
    return treeCount

test="input03test.txt"
puzzleInput="input03.txt"
# Load the base map
baseMap = []
with open("2020/input/" + puzzleInput) as file:
    for line in file:
        r = []
        r = list(line)
        baseMap.append(r)

wrap = len(baseMap[0]) - 1
rows = len(baseMap)

result = \
    traverse(baseMap, 1, 1) \
    * traverse(baseMap, 1, 3) \
    * traverse(baseMap, 1, 5) \
    * traverse(baseMap, 1, 7) \
    * traverse(baseMap, 2, 1)

print("Tree count: " + str(result))
