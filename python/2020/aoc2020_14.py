# Day 14, 2020 

# INPUT_FILE = "input14test1.txt"
INPUT_FILE = "input14.txt"

instructions = []

# TWO36ONES = 0b1111_1111_1111_1111_1111_1111_1111_1111_1111
TWO36ONES = (0b1 << 36) - 1

def readFile(filename):
    global instructions
    with open("2020/input/" + filename) as file:
        for line in file:
            instructions.append(line.strip())

def getInstruction(instruction):
    mask = None
    address = None
    value = None
    if 'mask' in instruction:
        mask = instruction.split(' = ')[1]
        if len(mask) != 36:
            print("*** Mask is ", len(mask), " long ***")
            exit(1)
    else:
        memEntry = instruction.split(' = ')
        address = memEntry[0].replace('mem[', '').replace(']', '')
        value = memEntry[1]
    return (mask, address, value)

def convertMaskClearBits(mask):
    maskValue = TWO36ONES
    setZerosMask = TWO36ONES
    setOnesMask = 0
    for bit in range(36):
        maskBit = mask[35 - bit]
        if maskBit == '0':
            # always set to 0
            setZerosMask ^= (0b1 << bit)
        elif maskBit == '1':
            # always set to 1
            setBit = (0b1 << bit)
            setOnesMask |= setBit
    return (setZerosMask, setOnesMask)

def solvePart1():
    setZerosMask = 0
    setOnesMask = 0
    memory = {}
    for instruction in instructions:
        (mask, address, value) = getInstruction(instruction)
        if mask != None:
            (setZerosMask, setOnesMask) = convertMaskClearBits(mask)
        else:
            newValue = int(value) & setZerosMask
            newValue = newValue | setOnesMask
            memory[address] = newValue
    
    sum = 0
    for cell in memory:
        value = memory[cell]
        sum += value

    print("Part 1: Sum = ", sum)


if __name__ == "__main__":
    readFile(INPUT_FILE)
    solvePart1()