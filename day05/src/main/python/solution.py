class Line:
    def __init__(self, point=None):
        if point is None:
            point = {0, 0, 0, 0}
        self.startX = point[0]
        self.startY = point[1]
        self.endX = point[2]
        self.endY = point[3]

    def __str__(self):
        return "(" + str(self.startX) + "," + str(self.startY) + ") -> (" + str(self.endX) + "," + str(self.endY) + ")"

    def __repr__(self) -> str:
        return self.__str__()

    def is_simple(self):
        return (self.startX == self.endX) ^ (self.startY == self.endY)

    def expand_points(self):
        xdiff = -1 if self.startX > self.endX else 1
        ydiff = -1 if self.startY > self.endY else 1
        if self.startX == self.endX:
            return list((self.startX, y) for y in range(self.startY, self.endY + ydiff, ydiff))
        elif self.startY == self.endY:
            return list((x, self.startY) for x in range(self.startX, self.endX + xdiff, xdiff))
        else:
            return list(zip(range(self.startX, self.endX + xdiff, xdiff), range(self.startY, self.endY + ydiff, ydiff)))


def read_input():
    f = open('../resources/input', 'r')
    return list(
        map(lambda x: Line([int(number) for point in x.split(" -> ") for number in point.split(",")]), f.readlines())
    )


def count_bad_spots(lines):
    coordinates = [point for line in lines for point in line.expand_points()]
    count_map = {}
    for i in coordinates:
        count_map[i] = count_map.get(i, 0) + 1
    return sum(map(lambda x: x > 1, count_map.values()))


if __name__ == "__main__":
    line_list = read_input()
    print("part 1")
    simple_lines = [line for line in line_list if line.is_simple()]
    print(count_bad_spots(simple_lines))
    print("part 2")
    print(count_bad_spots(line_list))
