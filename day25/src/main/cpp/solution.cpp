#include <fstream>
#include <iostream>
#include <map>

using namespace std;
#define coordinate pair<int, int>
int yMax = 0;
int xMax = 0;

map<coordinate, char> readInputFile() {
  ifstream file("../resources/input");
  string s;
  auto y = 0;
  map<coordinate, char> grid;
  while (getline(file, s)) {
    for (int x = 0; x < s.length(); ++x) {
      if (s[x] != '.') {
        grid[coordinate(x, y)] = s[x];
      }
    }
    ++y;
    xMax = s.length();
  }
  yMax = y;
  return grid;
}

int main() {
  auto grid = readInputFile();
  bool changed = true;
  int count = 0;
  while (changed) {
    changed = false;
    auto next = grid;
    auto it = grid.begin();
    while (it != grid.end()) {
      if (it->second == '>' &&
          grid.find(coordinate((it->first.first + 1) % xMax,
                               it->first.second)) == grid.end()) {
        next[coordinate((it->first.first + 1) % xMax, it->first.second)] = '>';
        next.erase(it->first);
        changed = true;
      }
      it++;
    }
    grid = next;
    it = grid.begin();
    while (it != grid.end()) {
      if (it->second == 'v' &&
          grid.find(coordinate(it->first.first,
                               (it->first.second + 1) % yMax)) == grid.end()) {
        next[coordinate(it->first.first, (it->first.second + 1) % yMax)] = 'v';
        next.erase(it->first);
        changed = true;
      }
      it++;
    }
    grid = next;
    ++count;
    if (count > 1000) {
      changed = false;
    }
  }
  cout << "part 1" << endl << count << endl;
  return 0;
}