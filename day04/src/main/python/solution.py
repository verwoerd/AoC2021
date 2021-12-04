def read_input():
    f = open('../resources/input', 'r')
    number_list = list(map(lambda x: int(x), f.readline().split(",")))
    raw_cards = list(filter(lambda a: len(a) > 0, map(lambda x: list(map(lambda y: int(y), x.split())), f.readlines())))
    return number_list, raw_cards


def has_bingo(card):
    for x in range(5):
        bingo = True
        for y in range(5):
            bingo &= card[y][x] < 0
        if bingo:
            return True
    for y in range(5):
        bingo = True
        for x in range(5):
            bingo &= card[y][x] < 0
        if bingo:
            return True
    return False


def play_bingo(numbers, cards):
    bingo_completed = list()
    bingos = list()
    for number in numbers:
        for count in range(len(cards) // 5):
            index = 5 * count
            if index in bingo_completed:
                continue
            for y in range(5):
                for x in range(5):
                    if cards[index + y][x] == number:
                        cards[index + y][x] = -1
                        if has_bingo(cards[index:index + 5]):
                            bingo_completed.append(index)
                            bingos.append((number, cards[index:index + 5]))
    return bingos


def calculate_score(card, number):
    count = sum(map(lambda line: sum(filter(lambda x: x > 0, line)), card))
    return count * number


if __name__ == "__main__":
    numbers, cards = read_input()
    results = play_bingo(numbers, cards)
    print("part 1")
    firstNumber, firstCard = results[0]
    print(calculate_score(firstCard, firstNumber))
    print("part 2")
    firstNumber, firstCard = results[-1]
    print(calculate_score(firstCard, firstNumber))
