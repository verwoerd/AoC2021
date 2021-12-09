#include <fstream>
#include <iostream>
#include <map>
#include <queue>
#include <set>
#include <string>
#include <vector>
#include <algorithm>

using namespace std;

#define coordinate pair<int, int>

map<coordinate, int> readInputFile() {
  ifstream file("../resources/input");
  map<coordinate, int> result;
  string s;
  int x, y = -1, depth;
  while (getline(file, s)) {
    y++;
    for (int x = 0; x < s.length(); x++) {
      depth = s[x] - '0';
      result[coordinate(x, y)] = depth;
    }
  }
  return result;
}

vector<coordinate> adjacentCoordinates(coordinate c) {
  vector<coordinate> result;
  result.push_back(coordinate(c.first + 1, c.second));
  result.push_back(coordinate(c.first - 1, c.second));
  result.push_back(coordinate(c.first, c.second + 1));
  result.push_back(coordinate(c.first, c.second - 1));
  return result;
}

vector<pair<coordinate, int>> findLowPoints(map<coordinate, int> &heightMap) {
  vector<pair<coordinate, int>> result;
  for (auto const &pair : heightMap) {
    auto adjacent = adjacentCoordinates(pair.first);
    bool basin = true;
    for (auto const &neighbour : adjacent) {
      basin &= heightMap.find(neighbour) == heightMap.end() ||
               heightMap[neighbour] > pair.second;
    }
    if (basin) {
      result.push_back(pair);
    }
  }
  return result;
}

int calculteLowPoints(map<coordinate, int> heightMap) {
  auto lowPoints = findLowPoints(heightMap);
  int result = 0;
  for (auto const &pair : lowPoints) {
    result += 1 + pair.second;
  }
  return result;
}

int calculateBasins(map<coordinate, int> heightMap) {
  auto lowPoints = findLowPoints(heightMap);
  vector<int> basinSize;
  for (auto const &next : lowPoints) {
    set<coordinate> seen;
    queue<pair<coordinate, int>> q;
    seen.insert(next.first);
    q.push(next);
    while (q.size() != 0) {
      auto current = q.front();
      q.pop();
      if (current.second == 8) {
        continue;
      }
      for (auto const &neighbour : adjacentCoordinates(current.first)) {
        if (heightMap.find(neighbour) == heightMap.end() ||
            seen.find(neighbour) != seen.end() || heightMap[neighbour] == 9 ||
            heightMap[neighbour] < current.second) {
          continue;
        }
        seen.insert(neighbour);
        q.push(pair(neighbour, heightMap[neighbour]));
      }
    }
    basinSize.push_back(seen.size());
  }
    sort(basinSize.begin(), basinSize.end());
    int result = 1;
    for(auto current = basinSize.rbegin(); current != basinSize.rbegin()+3; ++current) {
        result *= *current;
    }
    return result;
}

int main() {
  auto depthMap = readInputFile();
  cout << "part 1" << endl << calculteLowPoints(depthMap) << endl
     << "part 2" << endl
     << calculateBasins(depthMap) << endl;
  return 0;
}