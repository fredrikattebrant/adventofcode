# AoC Day 15, 2020

def solveIt(input, breakAt, partNo):
    lastTime = {} # key == number, values == last turns
    turn = 0
    # starting rounds:
    lastSpoken = -1
    for ix in input:
        turn += 1
        lastTime[ix] = [turn]
        lastSpoken = ix

    # start the game
    while turn < breakAt:
        turn += 1
        turns = lastTime[lastSpoken]
        if len(turns) == 1:
            # only spoken once
            if turns[0] == turn - 1:
                # spoken first time last turn - speak 0
                lastSpoken = 0
                turns = lastTime[lastSpoken]
                turns = [turns[len(turns) - 1], turn]
                lastTime[lastSpoken] = turns
            else:
                # speak the number of turns apart
                diff = turn - turns[0]
                lastSpoken
                turns = [turns[0], turn]
                lastTime[lastSpoken] = turns
        else:
            diff = turns[1] - turns[0]
            if diff in lastTime.keys():
                turns = lastTime[diff]
                lastTime[diff] = [turns[len(turns) - 1], turn]
            else:
                lastTime[diff] = [turn]
            lastSpoken = diff
    print("Part ", partNo, " Turn:", turn, " last spoken: ", lastSpoken)


testInput = [0, 3, 6]
puzzleInput = [20,0,1,11,6,3]

if __name__ == "__main__":
    solveIt(puzzleInput, 2020, 1)
    solveIt(puzzleInput, 30_000_000, 2)