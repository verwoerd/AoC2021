def read_input():
    f = open('../resources/input', 'r')
    y = -1
    coordinates = {}
    for line in f.readlines():
        y += 1
        for x in range(len(line.strip())):
            coordinates[(x, y)] = int(line[x])
    return coordinates


def adjacent_circulair_coordinates(x, y):
    return [(x + 1, y - 1), (x + 1, y), (x + 1, y + 1), (x - 1, y - 1), (x - 1, y), (x - 1, y + 1), (x, y + 1),
            (x, y - 1), ]


def simulate_lights(octopuses):
    queue = []
    new_state = {}
    for coordinate in octopuses:
        state = octopuses[coordinate]
        new_state[coordinate] = state + 1
        if state == 9:
            new_state[coordinate] = 0
            queue.append(coordinate)
    while len(queue) != 0:
        current = queue.pop()
        for coordinate in adjacent_circulair_coordinates(current[0], current[1]):
            if coordinate not in new_state:
                continue
            new_state[coordinate] += 1
            if new_state[coordinate] == 10:
                queue.append(coordinate)
                new_state[coordinate] = 0
            elif new_state[coordinate] == 1:
                new_state[coordinate] = 0
    return new_state


def part1(initial_state):
    state = initial_state
    count = 0
    for i in range(100):
        state = simulate_lights(state)
        count += sum(map(lambda x: x == 0, state.values()))
    return count


def part2(initial_state):
    state = initial_state
    for i in range(1, 99999999999999999999999999999999999999999999999):
        state = simulate_lights(state)
        if sum(map(lambda x: x == 0, state.values())) == len(state):
            return i


if __name__ == "__main__":
    dumbo_octopuses = read_input()
    print("part 1")
    print(part1(dumbo_octopuses))
    print("part 2")
    print(part2(dumbo_octopuses))
