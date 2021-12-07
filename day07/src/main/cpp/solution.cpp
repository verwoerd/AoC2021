#include <fstream>
#include <iostream>
#include <vector>

using namespace std;

vector<int> readInput() {
  ifstream file("../resources/input");
  vector<int> positions;
  for (int i; file >> i;) {
    positions.push_back(i);
    if (file.peek() == ',') {
      file.ignore();
    }
  }
  return positions;
}

long long moveCrabsCheapFuel(vector<int> const &crabs) {
  long long best = __LONG_LONG_MAX__;
  int min = INT32_MAX;
  int max = INT32_MIN;
  for (auto const &crab : crabs) {
    if (crab < min) {
      min = crab;
    }
    if (crab > max) {
      max = crab;
    }
  }
  for (int i = min; i <= max; i++) {
    long long count = 0;
    for (auto const &crab : crabs) {
      count += i < crab ? crab - i : i - crab;
    }
    if (count < best) {
      best = count;
    }
  }
  return best;
}

long long moveCrabsExpensiveFuel(vector<int> const &crabs) {
  long long best = __LONG_LONG_MAX__;
  int min = INT32_MAX;
  int max = INT32_MIN;
  for (auto const &crab : crabs) {
    if (crab < min) {
      min = crab;
    }
    if (crab > max) {
      max = crab;
    }
  }
  for (int i = min; i <= max; i++) {
    long long count = 0;
    long long diff;
    for (auto const &crab : crabs) {
      diff = i < crab ? crab - i : i - crab;
      count += (diff * (diff + 1)) / 2;
    }
    if (count < best) {
      best = count;
    }
  }
  return best;
}

int main() {
  vector<int> crabs = readInput();
  cout << "part 1" << endl
       << moveCrabsCheapFuel(crabs) << endl
       << "part 2" << endl
       << moveCrabsExpensiveFuel(crabs) << endl;
  return 0;
}