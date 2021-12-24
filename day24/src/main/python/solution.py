from functools import cache


def read_input():
    f = open('../resources/input', 'r')
    i = 0
    xVal = yVal = zVal = 0
    for line in f.readlines():
        if i % 18 == 4:
            zVal = int(line[6:-1])
        elif i % 18 == 5:
            xVal = int(line[6:-1])
        elif i % 18 == 15:
            yVal = int(line[6:-1])
            variables.append([zVal, xVal, yVal])
        i += 1
    calculate_z_values()


def calculate_z_values():
    for i in range(0, 14):
        index = 13 - i
        if len(z_values) == 0:
            z_values.append(variables[index][0])
        else:
            z_values.append(variables[index][0] * z_values[-1])
    z_values.reverse()


def check_digit(w, z, xAdd, zDiv, yAdd):
    x = xAdd + z % 26
    z = z // zDiv
    if x != w:
        z = z * 26 + w + yAdd
    return z


@cache
def smart_search(index, z):
    if index == 14:
        if z == 0:
            return [""]
        else:
            return []
    if z > z_values[index]:
        return []
    zDiv, xAdd, yAdd = variables[index]
    result = []
    for w in range(1, 10):
        next_z = check_digit(w, z, xAdd, zDiv, yAdd)
        for option in smart_search(index + 1, next_z):
            result.append(str(w) + option)
    return result


z_values = []
variables = []

if __name__ == "__main__":
    read_input()
    results = smart_search(0, 0)
    print("part 1")
    print(max(results))
    print("part 2")
    print(min(results))
