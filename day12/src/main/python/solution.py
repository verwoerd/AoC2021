def read_input():
    f = open('../resources/input', 'r')
    routes = dict()
    for line in f.readlines():
        start, end = line.strip().split("-")
        if start not in routes.keys():
            routes[start] = []
        if end not in routes.keys():
            routes[end] = []
        routes[start].append(end)
        routes[end].append(start)
    return routes


def find_possible_routes(routes, double=False):
    queue = [["start", {'start'}, False]]
    count = 0
    while len(queue) != 0:
        node, original, doubled = queue.pop()
        for target in routes[node]:
            seen = original.copy()
            if target == "end":
                count += 1
            elif target == "start":
                continue
            elif target.islower():
                if target not in seen:
                    seen.add(target)
                    queue.append([target, seen, doubled])
                elif double and not doubled:
                    queue.append([target, seen, True])
            else:
                queue.append([target, seen, doubled])
    return count


if __name__ == "__main__":
    route_list = read_input()
    print("part 1")
    print(find_possible_routes(route_list))
    print("part 2")
    print(find_possible_routes(route_list, True))
