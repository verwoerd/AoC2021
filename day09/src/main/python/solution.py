def read_input():
    f = open('../resources/input', 'r')
    # f = open('../../test/resources/part1/example1.input', 'r')
    y = -1
    coordinates = {}
    for line in f.readlines():
        y += 1
        for x in range(len(line.strip())):
            coordinates[(x, y)] = int(line[x])
    return coordinates


def adjacent_coordinates(x, y):
    return [(x + 1, y), (x - 1, y), (x, y + 1), (x, y - 1)]


def find_basins(height_map):
    basins = []
    for coordinate in height_map.keys():
        height = height_map[coordinate]

        if (sum(map(lambda c: c not in height_map or height_map[c] > height,
                    adjacent_coordinates(coordinate[0], coordinate[1]))) == 4):
            basins.append(coordinate)
    return basins


def find_largest_basins(heigh_map):
    basins = map(lambda x: (x, heigh_map[x]), find_basins(heigh_map))
    results = []
    for coordinate, heigth in basins:
        seen = {coordinate}
        queue = [(coordinate, heigth)]
        while len(queue) != 0:
            current, depth = queue.pop()
            if current == 8:
                continue
            neighbours = filter(
                lambda x: x in heigh_map and x not in seen and heigh_map[x] != 9 and heigh_map[x] > depth,
                adjacent_coordinates(current[0], current[1]))
            for n in neighbours:
                seen.add(n)
                queue.append((n, heigh_map[n]))
        results.append(len(seen))
    results = sorted(results, reverse=True)
    return results[0] * results[1] * results[2]


if __name__ == "__main__":
    height_map = read_input()
    print("part 1")
    print(sum(map(lambda x: 1 + height_map[x], find_basins(height_map))))
    print("part 2")
    print(find_largest_basins(height_map))
