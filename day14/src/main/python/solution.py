from collections import Counter


def read_input():
    f = open('../resources/input', 'r')
    template = f.readline().strip()
    f.readline()
    translation = {}
    for line in f.readlines():
        a, b = line.strip().split(" -> ")
        translation[a] = [a[0] + b, b + a[1]]
    return template, translation


def calculate_polymer_template(template, translation, times):
    current = Counter()
    for i in range(1, len(template)):
        s = template[i - 1] + template[i]
        current[s] += 1
    for _ in range(times):
        next_value = Counter()
        for key in current:
            for y in translation[key]:
                next_value[y] += current[key]
        current = next_value

    result = Counter()
    result[template[-1]] += 1
    for val in current:
        result[val[0]] += current[val]
    return result.most_common(1)[0][1] - result.most_common()[-1][1]


if __name__ == "__main__":
    template_string, translation_map = read_input()
    print("part 1")
    print(calculate_polymer_template(template_string, translation_map, 10))
    print("part 2")
    print(calculate_polymer_template(template_string, translation_map, 40))
