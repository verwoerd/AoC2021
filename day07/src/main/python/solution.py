def read_input():
    f = open('../resources/input', 'r')
    return list(map(lambda x: int(x), f.readline().split(",")))


def find_fuel_simple(input):
    best = 9999999999999999999999999999999999999999
    for i in range(min(input), max(input)):
        count = sum(map(lambda p: p - i if p >= i else i - p, input))
        if count < best:
            best = count
    return best


def find_fuel_proper(input):
    best = 9999999999999999999999999999999999999999
    for i in range(min(input), max(input)):
        count = sum(map(lambda d: (d*(d+1))//2, map(lambda p: p - i if p >= i else i - p, input)))
        if count < best:
            best = count
    return best


if __name__ == "__main__":
    crabs = read_input()
    print("part 1")
    print(find_fuel_simple(crabs))
    print("part 2")
    print(find_fuel_proper(crabs))
