#include <fstream>
#include <iostream>
#include <map>
#include <queue>
#include <set>
#include <string>
#include <vector>

using namespace std;
#define coordinate pair<int, int>
#define pic pair<int, coordinate>

map<coordinate, int> readInputFile() {
  ifstream file("../resources/input");
  map<coordinate, int> riskMap;
  string s;
  int y = -1;
  while (getline(file, s)) {
    ++y;
    int x = -1;
    for (auto const &risk : s) {
      riskMap[coordinate(++x, y)] = risk - '0';
    }
  }
  return riskMap;
}

vector<coordinate> adjacentCoordinates(coordinate c) {
  vector<coordinate> result;
  result.push_back(coordinate(c.first + 1, c.second));
  result.push_back(coordinate(c.first - 1, c.second));
  result.push_back(coordinate(c.first, c.second + 1));
  result.push_back(coordinate(c.first, c.second - 1));
  return result;
}

coordinate findMax(map<coordinate, int> &riskMap) {
  auto target = coordinate(0, 0);
  for (auto const &p : riskMap) {
    if (p.first.first > target.first || p.first.second > target.second) {
      target = p.first;
    }
  }
  return target;
}

int dijkstra(map<coordinate, int> riskMap) {
  set<coordinate> seen;
  seen.insert(coordinate(0, 0));
  priority_queue<pic, vector<pic>, greater<pic>> pqueue;
  pqueue.push(pair(0, coordinate(0, 0)));
  auto target = findMax(riskMap);
  while (!pqueue.empty()) {
    auto current = pqueue.top();
    pqueue.pop();
    if (current.second == target) {
      return current.first;
    }
    for (auto const &target : adjacentCoordinates(current.second)) {
      if (seen.insert(target).second && riskMap.find(target) != riskMap.end()) {
        pqueue.push(pair(current.first + riskMap[target], target));
      }
    }
  }
  throw 42;
}

map<coordinate, int> growMaze(map<coordinate, int> &riskMap) {
  auto maxRange = findMax(riskMap);
  int xRange = maxRange.first + 1;
  int yRange = maxRange.second + 1;
  coordinate c;
  map<coordinate, int> resultMap;
  for (int yFactor = 0; yFactor < 5; ++yFactor) {
    for (int xFactor = 0; xFactor < 5; ++xFactor) {
      for (auto const &p : riskMap) {
        auto risk = p.second + xFactor + yFactor;
        auto x = p.first.first + xFactor * xRange;
        auto y = p.first.second + yFactor * yRange;
        resultMap[coordinate(x, y)] = (risk < 10 ? risk : risk - 9);
      }
    }
  }
  return resultMap;
}

int main() {
  auto input = readInputFile();
  cout << "part 1" << endl
       << dijkstra(input) << endl
       << "part 2" << endl
       << dijkstra(growMaze(input)) << endl;
  return 0;
}
