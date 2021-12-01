# Day 13, 2020 

# INPUT_FILE = "input13test1.txt"
INPUT_FILE = "input13.txt"

earliest = 0
busIds = []

def readFile(filename):
    global earliest
    global busIds
    with open("2020/input/" + filename) as file:
        first = True
        for line in file:
            if first:
                first = False
                earliest = int(line.strip())
            else:
                for busId in list(line.strip().replace(',x', '').split(',')):
                    busIds.append(int(busId))

def getTimestampsTo(busId, departure):
    timestamps = []
    time = 0
    while time < departure: # add 10 for some margin
        time += busId
        timestamps.append(time)
    return timestamps

def getClosestDeparture(busId, departure):
    time = 0
    while True:
        time += busId
        if time > departure:
            return time

if __name__ == "__main__":
    readFile(INPUT_FILE)
    print(earliest)
    print(busIds)

    # table = {}
    # for busId in busIds:
    #     table[busId] = getTimestampsTo(busId, earliest)

    delta = earliest * 2 # after earliest departure
    earliestBusId = -1
    closestTime = -1
    for busId in busIds:
        closestTime = getClosestDeparture(busId, earliest)
        newDelta = closestTime - earliest
        if newDelta < delta:
            delta = newDelta
            earliestBusId = busId

    print("Earliest bus: ", earliestBusId)
    waitingTime = delta
    print("Departure: ", waitingTime)
    print("Part 1: ", waitingTime * earliestBusId)
