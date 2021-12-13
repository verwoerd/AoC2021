#include <fstream>
#include <iostream>
#include <set>
#include <string>
#include <vector>

using namespace std;
#define coordinate pair<int, int>
#define instruction pair<string, int>

pair<set<coordinate>, vector<instruction>> readInputFile() {
  ifstream file("../resources/input");
  set<coordinate> coordinates;
  vector<instruction> instructions;
  bool instr = false;
  string s, d;
  int i, delim, x, y;
  while (getline(file, s)) {
    if (s == "") {
      instr = true;
    } else if (instr) {
      d = s.substr(11, 1);
      i = stoi(s.substr(13, 9));
      instructions.push_back(instruction(d, i));
    } else {
      delim = s.find(",");
      x = stoi(s.substr(0, delim));
      y = stoi(s.substr(delim + 1, s.length()));
      coordinates.insert(coordinate(x, y));
    }
  }
  return pair(coordinates, instructions);
}

coordinate foldCoordinate(coordinate c, instruction i) {
  int x = c.first;
  int y = c.second;
  if (i.first == "x" && x > i.second)
    return coordinate(2 * i.second - x, y);
  if (i.first == "y" && y > i.second)
    return coordinate(x, 2 * i.second - y);
  return c;
}

set<coordinate> foldPage(set<coordinate> &coordinates, instruction i) {
  set<coordinate> result;
  for (auto const &c : coordinates) {
    result.insert(foldCoordinate(c, i));
  }
  return result;
}

void printFoldedPages(set<coordinate> coordinates,
                      vector<instruction> instructions) {
  set<coordinate> current = coordinates;
  for (auto const &i : instructions) {
    current = foldPage(current, i);
  }
  for (int y = 0; y < 6; y++) {
    for (int x = 0; x < 40; x++) {
      if (current.find(coordinate(x, y)) == current.end()) {
        cout << ".";
      } else {
        cout << "#";
      }
    }
    cout << endl;
  }
}

int main() {
  auto input = readInputFile();
  cout << "part 1" << endl
       << foldPage(input.first, input.second[0]).size() << endl
       << "part 2" << endl;
  printFoldedPages(input.first, input.second);
  return 0;
}
