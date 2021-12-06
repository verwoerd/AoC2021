#include <fstream>
#include <iostream>
#include <map>
#include <string>

using namespace std;
#define MILL map<int, long long>

MILL readInputFile() {
  ifstream file("../resources/input");
  MILL frequency;
  for (int i; file >> i;) {
    frequency[i]++;
    if (file.peek() == ',') {
      file.ignore();
    }
  }
  return frequency;
}

long long sleepWithFishies(int nights, MILL &start) {
  MILL current = start;
  for (int night = 0; night < nights; night++) {
    MILL next;
    for (int i = 0; i < 9; i++) {
      next[i] = current[(i + 1) % 9];
    }
    next[6] += current[0];
    current = next;
  }
  long long count = 0;
  for (auto const &pair : current) {
    count += pair.second;
  }
  return count;
}

int main() {
  MILL input = readInputFile();
  cout << "part 1" << endl
       << sleepWithFishies(80, input) << endl
       << "part 2" << endl
       << sleepWithFishies(256, input) << endl;
  return 0;
}