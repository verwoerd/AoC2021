def read_input():
    f = open('../resources/input', 'r')
    coordinates = set()
    instructions = []
    complete = False
    for line in f.readlines():
        if line.strip() == '':
            complete = True
        elif complete:
            d, i = line.strip().split("=")
            instructions.append((d[-1], int(i)))
        else:
            x, y = line.strip().split(',')
            coordinates.add((int(x), int(y)))
    return coordinates, instructions


def do_fold(coordinates, instruction):
    return set(map(lambda c: coordinate_fold(c, instruction), coordinates))


def coordinate_fold(coordinate, instruction):
    d, i = instruction
    x, y = coordinate
    if d == 'x' and x > i:
        return 2 * i - x, y
    elif d == 'y' and y > i:
        return x, 2 * i - y
    return x, y


def fold_result(coordinates, instructions):
    current = coordinates.copy()
    for instruction in instructions:
        current = do_fold(current, instruction)
    result = ""
    for y in range(0, 6):
        for x in range(0, 40):
            result += '#' if (x, y) in current else '.'
        result += '\n'
    return result


if __name__ == "__main__":
    initial_coordinates, instructin_list = read_input()
    print("part 1")
    print(len(do_fold(initial_coordinates, instructin_list[0])))
    print("part 2")
    print(fold_result(initial_coordinates, instructin_list))
