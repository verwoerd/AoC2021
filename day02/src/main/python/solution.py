def read_input():
    f = open('../resources/input', 'r')
    return list(map(lambda x: x.split(), f.readlines()))


def execute_simple_route(route):
    depth = forward = 0
    for x in route:
        direction = int(x[1])
        if x[0] == 'forward':
            forward += direction
        elif x[0] == 'down':
            depth += direction
        else:
            depth -= direction
    return depth * forward


def execute_manual_route(route):
    depth = forward = aim = 0
    for x in route:
        direction = int(x[1])
        if x[0] == 'forward':
            forward += direction
            depth += aim * direction
        elif x[0] == 'down':
            aim += direction
        else:
            aim -= direction
    return depth * forward


if __name__ == "__main__":
    route = read_input()
    print("part 1")
    print(execute_simple_route(route))
    print("part 2")
    print(execute_manual_route(route))
