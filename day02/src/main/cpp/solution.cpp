#include <iostream>
#include <utility>
#include <fstream>
#include <vector>
#include <string>
#include <sstream>

#define VPSLL vector<pair<Direction, long long>>

using namespace std;

enum Direction {
    up,
    down,
    forward
};

Direction fromString(string value) {
    if (value == "up")
        return up;
    if (value == "down")
        return down;
    return Direction::forward;
}

pair<Direction, long long> parseLine(string line) {
    stringstream ss(line);
    string direction;
    string amount;
    ss >> direction >> amount;
    return pair(fromString(direction), stoll(amount));
}

VPSLL readInputFile() {
    ifstream file("../resources/input");
    VPSLL lines;
    string s;
    while (getline(file, s))
    {
        lines.push_back(parseLine(s));
    }
    return lines;
}

long long executeSimpleRoute(VPSLL route) {
    long long depth = 0;
    long long position = 0;
    for (auto const &value : route)
    {
        switch (value.first)
        {
        case up:
            depth -= value.second;
            break;
        case down:
            depth += value.second;
            break;
        default:
            position += value.second;
            break;
        }
    }
    return depth * position;
}

long long executeManualRoute(VPSLL route) {
    long long depth = 0;
    long long position = 0;
    long long aim = 0;
    for (auto const &value : route) {
        switch (value.first) {
        case up:
            aim -= value.second;
            break;
        case down:
            aim += value.second;
            break;
        default:
            position += value.second;
            depth += value.second * aim;
            break;
        }
    }
    return depth * position;
}

int main() {
    VPSLL route = readInputFile();
    cout << "part 1" << endl
         << executeSimpleRoute(route) << endl
         << "part 2" << endl
         << executeManualRoute(route) << endl;
    return 0;
}
