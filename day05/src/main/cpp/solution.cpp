#include <fstream>
#include <iostream>
#include <map>
#include <regex>
#include <sstream>
#include <string>
#include <vector>

using namespace std;
#define coordinate pair<int, int>

struct line {
  coordinate start;
  coordinate end;
};

const regex REGEX("(\\d+),(\\d+) -> (\\d+),(\\d+)");

vector<line> readInputFile() {
  ifstream file("../resources/input");
  string s;
  vector<line> lines;
  int x1, x2, y1, y2;
  smatch match;
  while (getline(file, s)) {
    regex_search(s, match, REGEX);
    struct line result = {.start = {stoi(match[1]), stoi(match[2])},
                          .end = {stoi(match[3]), stoi(match[4])}};

    lines.push_back(result);
  }
  return lines;
}

bool isSimple(line current) {
  return (current.end.first == current.start.first) ^
         (current.end.second == current.start.second);
}

int calculate(vector<line> &lines, bool onlySimple) {
  map<coordinate, int> frequencyMap;
  for (auto const &current : lines) {
    if (onlySimple && !isSimple(current)) {
      continue;
    }
    int xDirection = current.end.first > current.start.first ? 1 : -1;
    int yDirection = current.end.second > current.start.second ? 1 : -1;
    if (current.start.first == current.end.first) {
      for (int y = current.start.second; y != current.end.second + yDirection;
           y += yDirection) {
        coordinate location = {current.start.first, y};
        frequencyMap[location] += 1;
      }
    } else if (current.start.second == current.end.second) {
      for (int x = current.start.first; x != current.end.first + xDirection;
           x += xDirection) {
        coordinate location = {x, current.start.second};
        frequencyMap[location] += 1;
      }
    } else {
      int y = current.start.second;
      for (int x = current.start.first; x != current.end.first + xDirection &&
                                        y != current.end.second + yDirection;
           x += xDirection) {
        coordinate location = {x, y};
        frequencyMap[location] += 1;
        y += yDirection;
      }
    }
  }
  int count = 0;
  for (auto const &entry : frequencyMap) {
    if (entry.second > 1)
      ++count;
  }
  return count;
}

int main() {
  vector<line> lines = readInputFile();
  cout << "part 1" << endl
       << calculate(lines, true) << endl
       << "part 2" << endl
       << calculate(lines, false) << endl;
  return 0;
}