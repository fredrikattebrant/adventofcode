# Day 10, 2020 

# INPUT_FILE = "input10test3.txt"
INPUT_FILE = "input10test1.txt"
# INPUT_FILE = "input10test2.txt"
# INPUT_FILE = "input10.txt"

def readFile(filename):
    numbers = []
    with open("2020/input/" + filename) as file:
        for line in file:
            number = int(line)
            numbers.append(number)
    return numbers

def getNexts(nums):
    start = nums[0]
    nexts = []
    for ix in range(3):
        numIx = ix + 1
        if numIx >= len(nums):
            break
        next = nums[numIx]
        if next - start > 3:
            break
        nexts.append(next)
    return nexts

# nums: list of numbers
def findPaths(nums, dict = {}, depthsDict = {}):
    start = nums[0]
    if len(nums) == 1:
        depthDict[start] = 1
    nexts = getNexts(nums)
    dict[start] = nexts
    paths = []
    for next in nexts:
        if next in dict:
            continue
        ix = nums.index(next)
        nextPaths = findPaths(nums[ix:], dict, depthDict)
    #     for nextPath in nextPaths:
    #         path = [start]
    #         path.extend(nextPath)
    #         paths.append(path)
    # if not nexts:
    #     paths.append([start])
    # return paths

###
if __name__ == "__main__":
    numbers = readFile(INPUT_FILE)
    sortedNums = sorted(numbers)
    print(sortedNums)
    joltage = 0
    counts = [0, 0, 0]
    for curr in sortedNums[0:len(sortedNums)]:
        diff = curr - joltage
        joltage = curr
        if diff > 3:
            print("*** Fail - diff is: ", diff, " a index: ", curr)
            exit(1)
        counts[diff - 1] += 1
    # final step is +3:
    joltage += 3
    counts[2] += 1
    print(counts)
    print("Final joltage: ", joltage)
    print("Part 1 - Result: ", counts[0] * counts[2])

    print("My find path:")
    start = sortedNums[0]
    end = sortedNums[len(sortedNums) - 1]
    sortedNums.insert(0, 0)
    sortedNums.append(sortedNums[-1] + 3)
    myPathDict = {} 
    depthDict = {}
    findPaths(sortedNums, myPathDict, depthDict)

    sum = 1
    for key in myPathDict:
        vals = myPathDict[key]
        pad = ''
        if key < 10:
            pad = ' '
        print(pad, key, ': ', len(vals), ', ', vals)
        #print(len(vals))
        if len(vals) > 1:
            sum += len(vals)
    print("Result 2: ", sum)

