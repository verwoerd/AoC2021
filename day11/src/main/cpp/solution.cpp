#include <fstream>
#include <iostream>
#include <map>
#include <queue>
#include <set>
#include <string>
#include <vector>

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
  result.push_back(coordinate(c.first + 1, c.second - 1));
  result.push_back(coordinate(c.first + 1, c.second));
  result.push_back(coordinate(c.first + 1, c.second + 1));
  result.push_back(coordinate(c.first - 1, c.second - 1));
  result.push_back(coordinate(c.first - 1, c.second));
  result.push_back(coordinate(c.first - 1, c.second + 1));
  result.push_back(coordinate(c.first, c.second + 1));
  result.push_back(coordinate(c.first, c.second - 1));
  return result;
}

map<coordinate, int> doRound(map<coordinate, int> &initialState) {
  map<coordinate, int> state;
  queue<coordinate> doubleCheck;
  for (auto const &pair : initialState) {
    if (pair.second == 9) {
      state[pair.first] = 0;
      doubleCheck.push(pair.first);
    } else {
      state[pair.first] = pair.second + 1;
    }
  }
  while (doubleCheck.size() != 0) {
    auto current = doubleCheck.front();
    doubleCheck.pop();
    for (auto const &c : adjacentCoordinates(current)) {
      if (state.find(c) == state.end()) {
        continue;
      }
      state[c] += 1;
      if (state[c] == 10) {
        state[c] = 0;
        doubleCheck.push(c);
      } else if (state[c] == 1) {
        state[c] = 0;
      }
    }
  }
  return state;
}

int part1(map<coordinate, int> &initial_state) {
  int count = 0;
  map<coordinate, int> state = initial_state;
  for (int i = 0; i <= 100; i++) {
    state = doRound(state);
    for (auto const &pair : state) {
      if (pair.second == 0) {
        count++;
      }
    }
  }
  return count;
}

int part2(map<coordinate, int> &initial_state) {
  int count = 1;
  map<coordinate, int> state = initial_state;
  while (true) {
    state = doRound(state);
    bool synced = true;
    for (auto const &pair : state) {
      synced &= pair.second == 0;
    }
    if (synced) {
      return count;
    }
    ++count;
  }
}

int main() {
  auto octopusGarden = readInputFile();
  cout << "part 1" << endl
       << part1(octopusGarden) << endl
       << "part 2" << endl
       << part2(octopusGarden) << endl;
  return 0;
}
