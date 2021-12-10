def read_input():
    f = open('../resources/input', 'r')
    # f = open('../../test/resources/part1/example1.input', 'r')
    return list(map(lambda l: parse_line(l.strip()), f.readlines()))


opening_chars = {'(', '<', '{', '['}


def closing_char(c):
    if c == '(':
        return ')'
    elif c == '<':
        return '>'
    elif c == '{':
        return '}'
    else:
        return ']'


def parse_line(line):
    invalid = ' '
    stack = []
    for c in line:
        if c in opening_chars:
            stack.append(c)
        elif closing_char(stack[-1]) == c:
            stack.pop()
        else:
            invalid = c
            break
    return invalid, stack


def score_compiler_error(errors):
    if errors[0] == ' ':
        return 0
    elif errors[0] == ')':
        return 3
    elif errors[0] == ']':
        return 57
    elif errors[0] == '}':
        return 1197
    else:
        return 25137


def score_compiler_errors(lines):
    return sum(map(score_compiler_error, lines))


def score_finish_error(errors):
    if errors[0] != ' ':
        return 0
    count = 0
    for c in reversed(errors[1]):
        count *= 5
        if closing_char(c) == ')':
            count += 1
        elif closing_char(c) == ']':
            count += 2
        elif closing_char(c) == '}':
            count += 3
        else:
            count += 4
    return count


def score_incomplete_errors(lines):
    scores = sorted(list(filter(lambda x: x != 0, map(score_finish_error, lines))))
    return scores[len(scores) // 2]


if __name__ == "__main__":
    instructions = read_input()
    print("part 1")
    print(score_compiler_errors(instructions))
    print("part 2")
    print(score_incomplete_errors(instructions))
