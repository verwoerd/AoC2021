if __name__ == "__main__":
    grid = {}
    y = 0
    x_max = 0
    y_max = 0
    f = open('../resources/input', 'r')
    for line in f.readlines():
        for x in range(0, len(line.strip())):
            if line[x] != '.':
                grid[(x, y)] = line[x]
        x_max = len(line) - 1
        y += 1
    y_max = y
    changed = True
    count = 1
    while changed:
        count += 1
        changed = False
        newGrid = grid.copy()
        for x, y in grid:
            value = grid[(x, y)]
            if value == '>' and ((x + 1) % x_max, y) not in grid.keys():
                newGrid.pop((x, y))
                newGrid[((x + 1) % x_max, y)] = '>'
                changed = True
        grid = newGrid.copy()
        for x, y in grid:
            value = grid[(x, y)]
            if value == 'v' and (x, (y + 1) % y_max) not in grid.keys():
                newGrid.pop((x, y))
                newGrid[(x, (y + 1) % y_max)] = 'v'
                changed = True
        grid = newGrid
    print("part 1")
    print(count)
