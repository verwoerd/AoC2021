import heapq


def read_input():
    f = open('../resources/input', 'r')
    y = -1
    risk_map = {}
    for line in f.readlines():
        y += 1
        x = -1
        for c in line.strip():
            x += 1
            risk_map[(x, y)] = int(c)
    return risk_map


def adjacent_coordinates(x, y):
    return [(x + 1, y), (x - 1, y), (x, y + 1), (x, y - 1)]


def find_max(maze):
    target = (0, 0)
    for key in maze:
        if key[0] > target[0] or key[1] > target[1]:
            target = key
    return target


def dijkstra(risk_map):
    queue = []
    heapq.heappush(queue, (0, (0, 0)))
    seen = set()
    target = find_max(risk_map)
    while len(queue) != 0:
        risk, current = heapq.heappop(queue)
        if current == target:
            return risk
        for option in adjacent_coordinates(current[0], current[1]):
            if option in risk_map and option not in seen:
                seen.add(option)
                heapq.heappush(queue, (risk + risk_map[option], option))


def grow(original):
    risk_map = {}
    x_range, y_range = find_max(original)
    x_range += 1
    y_range += 1
    for y_factor in range(5):
        for x_factor in range(5):
            for coordinate, risk in original.items():
                new_risk = risk + x_factor + y_factor
                risk_map[(coordinate[0] + x_factor * x_range, coordinate[1] + y_factor * y_range)] = \
                    new_risk if new_risk < 10 else new_risk - 10 + 1
    return risk_map


if __name__ == "__main__":
    risks = read_input()
    print("part 1")
    print(dijkstra(risks))
    print("part 2")
    print(dijkstra(grow(risks)))
