import re


def read_input():
    f = open('../resources/input', 'r')
    line = f.readline().strip()
    result = re.search(r'target area: x=(-?\d+)..(-?\d+), y=(-?\d+)..(-?\d+)', line)
    return (int(result.group(1)), int(result.group(2))), (int(result.group(3)), int(result.group(4)))


def simulate_trajectories(xRange, yRange):
    results = []
    for x in range(-xRange[1], xRange[1]+1):
        for y in range(yRange[0], -yRange[0]+1):
            coordinate = (0, 0)
            xValue = x
            yValue = y
            maxY = -999999
            while coordinate[0] + xValue <= xRange[1] and coordinate[1] + yValue >= yRange[0]:
                coordinate = (coordinate[0] + xValue, coordinate[1] + yValue)
                maxY = max(maxY, coordinate[1])
                yValue -= 1
                if xValue != 0:
                    xValue += 1 if xValue < 0 else -1
            if xRange[0] <= coordinate[0] <= xRange[1] and yRange[0] <= coordinate[1] <= yRange[1]:
                results.append(maxY)
    return results


if __name__ == "__main__":
    x_range, y_range = read_input()
    trajectories = simulate_trajectories(x_range, y_range)
    print("part 1")
    print(max(trajectories))
    print("part 2")
    print(len(trajectories))
