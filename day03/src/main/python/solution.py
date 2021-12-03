import math


def read_input():
    f = open('../resources/input', 'r')
    return list(map(lambda x: int(x, 2), f.readlines()))


def diagnose_power(diagnose):
    gamma = epsilon = 0
    highest = int(math.log2(max(diagnose))) + 1
    for x in range(highest):
        ones = sum(map(lambda a: (a >> x) & 1, diagnose))
        if ones * 2 > len(diagnose):
            gamma = gamma | (1 << x)
        else:
            epsilon = epsilon | (1 << x)
    return gamma * epsilon


def filter_output(diagnose, lsb):
    index = int(math.log2(max(diagnose)))
    current = diagnose.copy()
    while len(current) > 1:
        ones = sum(map(lambda a: (a >> index) & 1, current))
        if ones * 2 >= len(current):
            delete = 1 - lsb
        else:
            delete = lsb
        current = list(filter(lambda a: (a >> index) & 1 == delete, current))
        index = index - 1
    return current[0]


def diagnose_oxygen(diagnose):
    oxygen = filter_output(diagnose, 1)
    co2 = filter_output(diagnose, 0)
    return oxygen * co2


if __name__ == "__main__":
    output = read_input()
    print("part 1")
    print(diagnose_power(output))
    print("part 2")
    print(diagnose_oxygen(output))
