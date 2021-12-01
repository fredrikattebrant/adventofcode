# AoC Day 16, 2020
DEBUG=False
INACTIVE = '.'
ACTIVE   = '#'

cubes = {}

def debug(*s):
    if not DEBUG:
        return
    text = ""
    for t in s:
        text += str(t)
    print(s)

# *** FIXME *** bug in this method:
def activeNeighbourCount(x, y, z, size):
    DEBUG=False
    debug("Count for: ", x, y, z)
    activeCount = 0
    for x1 in range(x - 1, x + size - 1):
        for y1 in range(y - 1, y + size - 1):
            for z1 in range(z - 1, z + size - 1):
                key = (x1, y1, z1)
                debug("Checking: ", key)
                if x == x1 and y == y1 and z == z1:
                    debug("Skipping: ", key)
                    continue
                if key in cubes.keys():
                    c = cubes[key]
                    debug(" -> ", c)
                    if c == ACTIVE:
                        activeCount += 1
                else:
                    # inactive
                    activeCount += 0
    return activeCount


# load cubes, return size of side
def loadCubes(filename):
    global cubes
    with open("2020/input/" + filename) as file:
        x = 0
        z = 0
        for line in file:
            y = 0
            for c in list(line.strip()):
                cubes[x, y, 0] = c
                y += 1
            x += 1
            y = 0
    return x

def runCycle(z0, size):
    for x in range(z0, z0 + size):
        for y in range (z0, z0 + size):
            for z in range(z0, z0 + size):
                count = activeNeighbourCount(x, y, z, size)
                key = (x,y,z)
                if key in cubes.keys():
                    c = cubes[key]
                else:
                    c = INACTIVE
                if c == ACTIVE:
                    if not (count == 2 or count == 3):
                        c = INACTIVE
                else:
                    if count == 3:
                        c = ACTIVE
                cubes[key] = c

def printLayer(z0, size):
    global DEBUG
    DEBUG=True
    debug('z=', z0)
    for x in range(z0, z0 + size):
        row = ''
        for y in range (z0, z0 + size):
            key = (x,y,z0)
            if key in cubes.keys():
                c = cubes[key]
            else:
                c = INACTIVE
            row += c
        debug(row)
    debug()
    DEBUG=False


if __name__ == "__main__":
    side = loadCubes('input17test1.txt')
    debug("==========================")
    debug("Side: ", side)
    for key in cubes.keys():
        debug(key, " -> ", cubes[key])
    ac = activeNeighbourCount(1, 2, 0, side)
    print(ac)

    printLayer(0, side)
    runCycle(0, side)
    printLayer(0, side)
    printLayer(-1, side)
    

