# AoC Day 8, 2020
import re

def readFile(filename):
    instructions = []
    with open("2020/input/" + filename) as file:
        for line in file:
            split = line.split()
            instruction = split[0]
            offset = split[1].strip()
            instructions.append([instruction, offset])
    return instructions


def run1(fileName, live):
    instructions = readFile(fileName)
    acc = 0
    executed = set()
    current = 0
    while True:
        if current >= len(instructions):
            print("!!! Terminate at addr: ", current)
            break
        entry = instructions[current]
        (instr, arg) = entry
        entryValue = instr + arg
        if current in executed:
            break
        if not live and current in executed:
            break
        executed.add(current)
        if instr == 'nop':
            current += 1
        elif instr == 'acc':
            acc += int(arg)
            current += 1
        else: # jmp
            current += int(arg)
    return acc

def runInstructions(instructions):
    print()
    acc = 0
    executed = set()
    current = 0
    repeatCount = 0
    exitedOk = False
    while True:
        if current >= len(instructions):
            print("!!! Terminate at addr: ", current)
            exitedOk = True
            break
        entry = instructions[current]
        (instr, arg) = entry
        entryValue = instr + arg
        if current in executed:
            exitedOk = False
            print("!!! In a loop at: ", current, ", acc: ", acc)
            break
            #repeatCount += 1
        # if repeatCount > 10000:
        #     exitedOk = False
        #     break
        print("Executing: ", instr, arg)
        executed.add(current)
        if instr == 'nop':
            current += 1
        elif instr == 'acc':
            acc += int(arg)
            print("acc: ", acc)
            current += 1
        else: # jmp
            current += int(arg)
    return (acc, exitedOk) 

def run2(fileName, live):
    instructions = readFile(fileName)

    jmps = [] # list of indexes with jmp instructions
    for i in range(1, len(instructions) - 1):
        instr = instructions[i]
        if 'jmp' in instr:
            jmps.append(i)
    jmpIx = 0
   
    restoreIx = -1
    while True:
        (acc, exitedOk) = runInstructions(instructions)
        if exitedOk:
            return acc
        # replace jmp with nop and repeat
        if restoreIx > -1:
            instr = instructions[restoreIx]
            instr[0] = 'jmp'
        replaceIx = jmps[jmpIx]
        restoreIx = replaceIx
        print("!!! Replacing ix: ", replaceIx)
        instr = instructions[replaceIx]
        instr[0] = 'nop'
        #instructions[replaceIx] = instr
        jmpIx += 1
    
    
def solvePart1(useTestData):
    fileName = "input08.txt" if useTestData else "input08test1.txt"
    acc = run1(fileName, useTestData)
    print("Part 1: Accumulator = ", acc)


def solvePart2(useTestData):
    fileName = "input08.txt" if useTestData else "input08test2.txt"
    acc = run2(fileName, useTestData)
    print("Part 2: Accumulator = ", acc)


#
# Main
#
if __name__ == '__main__':
    solvePart1(True) # True:  Use puzzle input
    solvePart2(True) # False: Use test data
