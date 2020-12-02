# AOC 2020 Day 1, part 2
def checkSum(first, second, third):
    if first + second + third == 2020:
        print(str(first) + " + " + str(second) + " + " + str(third) + " = 2020")
        return first * second * third
    return 0


print("AOC 2020 Dec 01 #2:")
# lines = [1721,
# 979,
# 366,
# 299,
# 675,
# 1456]
lines = []
with open("input/input01.txt") as file:
   for line in file:
       line = line.strip()
       lines.append(line)

len = len(lines)
for i in range(len):
    iint = int(lines[i])
    for j in range(i+1, len):
        jint = int(lines[j])
        for k in range(j+1, len):
            kint = int(lines[k])
            result = checkSum(iint, jint, kint)
            if result != 0:
                print("Result => " + str(result))
                exit()

