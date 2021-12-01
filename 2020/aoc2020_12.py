# Day 12, 2020 

# INPUT_FILE = "input12test1.txt"
INPUT_FILE = "input12.txt"

instructions = list()
x = 0 # North: positive, South: negative
y = 0 # East: positive, West: negative
d = 0 # degrees
wpX = 0
wpY = 0

def readFile(filename):
    with open("2020/input/" + filename) as file:
        for line in file:
            instructions.append(line.strip())

def takeAction1(instruction):
    global x
    global y
    global d
    action = instruction[0]
    value = int(instruction[1:])
    # print(action, " ", value)
    if action == 'N':
        x += value
    elif action == 'S':
        x -= value
    elif action == 'E':
        y += value
    elif action == 'W':
        y -= value
    elif action ==  'L':
        d -= value
        d = d % 360
    elif action == 'R':
        d += value
        d = d % 360
    elif action == 'F':
        if d == 0:
            x += value
        elif d == 90:
            y += value
        elif d == 180:
            x -= value
        elif d == 270:
            y -= value
        else:
            print("ERROR: ", action, value)
            exit(1)
    else:
        print("ERROR: ", action, value)
        exit(2)

def rotateWaypoint(instr):
    global wpX
    global wpY
    # directions: L, R
    # degrees: 90, 180, 270
    if instr == 'L90' or instr == 'R270': 
        tmp = wpX
        wpX = wpY
        wpY = -tmp
    elif instr == 'L180' or instr == 'R180': 
        wpX = -wpX
        wpY = -wpY
    else: # L270 or R90
        tmp = wpX
        wpX = -wpY
        wpY = tmp 

def takeAction2(instruction):
    global x
    global y
    global wpX
    global wpY
    action = instruction[0]
    value = int(instruction[1:])
    if action == 'N':
        wpX += value
    elif action == 'S':
        wpX -= value
    elif action == 'E':
        wpY += value
    elif action == 'W':
        wpY -= value
    elif action ==  'L' or action == 'R':
        rotateWaypoint(instruction)
    elif action == 'F':
        x += value * wpX
        y += value * wpY
    else:
        print("ERROR: ", action, value)
        exit(2)

if __name__ == "__main__":
    readFile(INPUT_FILE)

    # Part 1:
    x = 0
    y = 0
    d = 90
    for instruction in instructions:
        takeAction1(instruction)

    manhattanDistance1 = abs(x) + abs(y)
    print("Part 1: Distance = ", manhattanDistance1)
            
    # Part 2:
    #     
    wpX = 1
    wpY = 10
    x = 0
    y = 0
    for instruction in instructions:
        takeAction2(instruction)
    
    manhattanDistance2 = abs(x) + abs(y)
    print("Part 2: Distance = ", manhattanDistance2)