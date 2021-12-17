#include <algorithm>
#include <fstream>
#include <iostream>
#include <regex>
#include <string>
#include <vector>

using namespace std;
#define coordinate pair<int, int>

pair<coordinate, coordinate> readInputFile() {
  ifstream file("../resources/input");
// ifstream file("../../test/resources/part1/example1.input");
  string s;
  smatch match;
  regex re("target area: x=(-?\\d+)..(-?\\d+), y=(-?\\d+)..(-?\\d+)");
  getline(file, s);
  regex_search(s, match, re);
  return pair(coordinate(stoi(match.str(1)), stoi(match.str(2))),
              coordinate(stoi(match.str(3)), stoi(match.str(4))));
}

vector<int> simulateTrajectoryHeights(coordinate xRange, coordinate yRange) {
  vector<int> result;
  for (int x = -xRange.second; x <= xRange.second; ++x) {
    for (int y = yRange.first; y <= -yRange.first; ++y) {
      coordinate current(0, 0);
      int xDiff = x, yDiff = y, maxY = INT32_MIN;
      while (current.first + xDiff <= xRange.second &&
             current.second + yDiff >= yRange.first) {
        current = coordinate(current.first + xDiff, current.second + yDiff);
        maxY = max(maxY, current.second);
        yDiff--;
        if (xDiff != 0) {
          xDiff += xDiff > 0 ? -1 : 1;
        }
      }
      if (current.first >= xRange.first && current.first <= xRange.second &&
          current.second >= yRange.first && current.second <= yRange.second) {
            //   cout << x << " " << y << " " << current.first << " "<< current.second << endl;
        result.push_back(maxY);
        
      }
    }
  }

  return result;
}

int main() {
  auto input = readInputFile();
  auto heights = simulateTrajectoryHeights(input.first, input.second);
  cout << "part 1" << endl
       <<  *max_element(heights.begin(), heights.end())
       << endl
       << "part 2" << endl
       << heights.size() << endl;
  return 0;
}