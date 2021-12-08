from functools import reduce

from more_itertools import one


def filter_length(lis, length):
    return [x for x in lis if len(x) == length]


class Digit:
    def __init__(self):
        self.top = ' '
        self.middle = ' '
        self.bottom = ' '
        self.topLeft = ' '
        self.topRight = ' '
        self.bottomLeft = ' '
        self.bottomRight = ' '

    def solve(self, description):
        one_value = one(filter_length(description, 2))
        seven = one(filter_length(description, 3))
        four = one(filter_length(description, 4))
        eight = one(filter_length(description, 7))
        self.top = one([d for d in seven if d not in one_value])
        sixers = filter_length(description, 6)
        six = one([x for x in sixers if sum(map(lambda d: d not in one_value, x)) == 5])
        nine = one([x for x in sixers if sum(map(lambda d: d in four, x)) == 4])
        self.topRight = one([d for d in eight if d not in six])
        self.bottomLeft = one([d for d in eight if d not in nine])
        self.bottomRight = one([d for d in one_value if d != self.topRight])
        self.bottom = one([d for d in nine if d not in four and d != self.top])
        zero = one([n for n in sixers if n != six and n != nine])
        self.topLeft = one([d for d in zero if
                            d != self.top and d != self.bottom
                            and d != self.topRight and d != self.bottomRight
                            and d != self.bottomLeft])
        self.middle = one([d for d in four if d != self.topLeft and d != self.topRight and d != self.bottomRight])

    def get_digit(self, line):
        return reduce(lambda acc, d: acc * 10 + self.get_single_digit(d), line, 0)

    def get_single_digit(self, d):
        if len(d) == 2:
            return 1
        elif len(d) == 3:
            return 7
        elif len(d) == 4:
            return 4
        elif len(d) == 5:
            if self.topLeft in d and self.bottomRight in d:
                return 5
            elif self.topRight in d and self.bottomRight in d:
                return 3
            else:
                return 2
        elif len(d) == 6:
            if self.middle not in d:
                return 0
            elif self.bottomLeft not in d:
                return 9
            else:
                return 6
        else:
            return 8


def read_input():
    f = open('../resources/input', 'r')
    descriptions = []
    displays = []
    for line in f.readlines():
        description, display = line.split("|")
        displays.append(display.strip().split(" "))
        descriptions.append(description.strip().split(" "))
    return descriptions, displays


def count_easy_digits(displays):
    return len([x for display in displays for x in display if (2 <= len(x) <= 4) or len(x) == 7])


def count_display_numbers(description, display):
    count = 0
    for i in range(len(description)):
        digit = Digit()
        digit.solve(description[i])
        count += digit.get_digit(display[i])
    return count


if __name__ == "__main__":
    desc, disp = read_input()
    print("part 1")
    print(count_easy_digits(disp))
    print("part 2")
    print(count_display_numbers(desc, disp))
