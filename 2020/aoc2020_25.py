# AoC Day 25, 2020

def transform(subjectNumber, loopSize):
    value = 1
    for i in range(loopSize):
        value = value * subjectNumber
        value = value % 20201227
    return value

def tryLoopSizes(key, subjectNumber):
    loopSize = 0
    value = 1
    while value != key:
        value = value * subjectNumber
        value = value % 20201227
        loopSize += 1
    return loopSize
    

def solvePart1(cardKey, doorKey):
    #cardLoopSize = tryLoopSizes(cardKey)
    #print("Card: ", cardKey, " loopSize: ", cardLoopSize)

    doorLoopSize = tryLoopSizes(doorKey, 7)
    print("Door: ", doorKey, " loopSize: ", doorLoopSize)

    #cardEncryptionKey = transform(doorKey, cardLoopSize)
    doorEncryptionKey = transform(cardKey, doorLoopSize)

    #print("E1: ", cardEncryptionKey)
    print("Encryption key: ", doorEncryptionKey)
    print("E2: ", tryLoopSizes(cardKey, doorLoopSize))

#
# Main
#
if __name__ == '__main__':
    #solvePart1(5764801, 17807724)
    solvePart1(3418282, 8719412)


