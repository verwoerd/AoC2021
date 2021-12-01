def count_increments(sonar):
    return sum([sonar[i + 1] > sonar[i] for i in range(len(sonar) - 1)])


f = open('../resources/input', 'r')
lines = list(map(int, f.readlines()))
print("part 1")
print(count_increments(lines))

grouped = [lines[i + 0] + lines[i + 1] + lines[i + 2] for i in range(len(lines) - 2)]
print("part 2")
print(count_increments(grouped))
