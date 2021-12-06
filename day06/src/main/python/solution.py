def read_input():
    f = open('../resources/input', 'r')
    parsed = list(map(lambda x: int(x), f.readline().split(",")))
    counts = [parsed.count(x) for x in range(9)]
    return counts


def calculate_fishes(count_list, times):
    current = count_list
    for _ in range(times):
        next_list = [0 for i in range(9)]
        for i in range(9):
            next_list[i] = current[(i + 1) % 9]
        next_list[6] += current[0]
        current = next_list
    return sum(current)


if __name__ == "__main__":
    counts = read_input()
    print(counts)
    print("part 1")
    print(calculate_fishes(counts, 80))
    print("part 2")
    print(calculate_fishes(counts, 256))
