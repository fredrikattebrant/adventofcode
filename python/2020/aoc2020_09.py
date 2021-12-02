# Day 9, 2020 

# PREAMBLE_SIZE = 5
# INPUT_FILE = "input09test1.txt"
PREAMBLE_SIZE = 25
INPUT_FILE = "input09.txt"

def readFile(filename):
    numbers = []
    with open("2020/input/" + filename) as file:
        for line in file:
            number = int(line)
            numbers.append(number)
    return numbers

# check the number at index
def checkNumbers(numbers, index):
    sum = numbers[index]
    # print("To check: ", sum)
    for i in range(index - PREAMBLE_SIZE, index - 1):
        numI = numbers[i]
        # print("i [", i, "] ", numI)
        for j in range(i + 1, index):
            numJ = numbers[j]
            sum2 = numI + numJ
            # print(" j [", j, "] ", numJ, " => ", sum2)
            if sum == sum2:
                return True
    return False

def getEncryptionWeakness(numbers, start, end):
    numbersToCheck = numbers[start:end]
    min1 = min(numbersToCheck)
    max2 = max(numbersToCheck)
    return min1 + max2

def sumUntilEqual(numbers, value):
    sum = 0
    for start in range(0, len(numbers)):
        sum = numbers[start]
        for next in range(start + 1, len(numbers)):
            sum += numbers[next]
            if sum == value:
                return getEncryptionWeakness(numbers, start, next)
            elif sum > value:
                break
    exit(2) # Fail - couldn't find matching sum

def solvePart1():
    numbers = readFile(INPUT_FILE)
    numberOfNumbers = len(numbers)
    for ix in range(PREAMBLE_SIZE, numberOfNumbers - PREAMBLE_SIZE):
        if not checkNumbers(numbers, ix):
            # print("First invalid number: ", numbers[ix], " at index: ", ix)
            return numbers[ix]
    exit(1) # Fail... would be crap input

def solvePart2(value):
    numbers = readFile(INPUT_FILE)
    return sumUntilEqual(numbers, value)

###
if __name__ == '__main__':
    part1number = solvePart1()
    print("Part 1: ", part1number)

    encryptionWeakness = solvePart2(part1number)
    print("Part 2: ", encryptionWeakness)
