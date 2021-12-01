def count_increments(sonar):
    count = 0
    for i in range(len(sonar) - 1):
        if sonar[i + 1] > sonar[i]:
            count = count + 1
    return count


f = open('../resources/input', 'r')
lines = list(map(int, f.readlines()))
print("part 1")
print(count_increments(lines))

grouped = [lines[i + 0] + lines[i + 1] + lines[i + 2] for i in range(len(lines) - 2)]
print("part 2")
print(count_increments(grouped))
