# AOC 2020 Day 1, part 1
print("AOC 2020 Dec 01 #1:")
lines = []
with open("2020/input/input01.txt") as file:
    for line in file:
        line = line.strip()
        lines.append(line)

len = len(lines)
for i in range(len):
    outer = int(lines[i])
    for j in range(i+1, len):
        inner = int(lines[j])
        sum = outer + inner
        if sum == 2020:
            result = outer * inner
            print(str(outer) + " + " + str(inner) + " => " + str(result))
            exit()
