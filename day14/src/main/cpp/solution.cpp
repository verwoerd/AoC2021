#include <fstream>
#include <iostream>
#include <map>
#include <string>
#include <vector>

using namespace std;

pair<string, map<string, vector<string>>> readInputFile() {
  ifstream file("../resources/input");
  string templat;
  getline(file, templat);
  string s, from, to;
  getline(file, s);
  map<string, vector<string>> translation;
  while (getline(file, s)) {
    from = s.substr(0, 2);
    to = s.substr(6, 1);
    vector<string> value;
    value.push_back(from.substr(0, 1) + to);
    value.push_back(to + from.substr(1, 1));
    translation[from] = value;
  }
  return pair(templat, translation);
}

long long calculate_polymer_template(string templat,
                                     map<string, vector<string>> translation,
                                     int times) {
  map<string, long long> current;
  for (int i = 0; i < templat.size() - 1; i++) {
    current.emplace(templat.substr(i, 2), 0);
    current[templat.substr(i, 2)] += 1;
  }
  for (int j = 0; j < times; j++) {
    map<string, long long> next;
    for (auto const &pair : current) {
      for (auto const &r : translation[pair.first]) {
        next.emplace(r, 0);
        next[r] += pair.second;
      }
    }
    current = next;
  }
  map<char, long long> frequency;
  for (auto const &c : current) {
    frequency.emplace(c.first[0], 0);
    frequency[c.first[0]] += c.second;
  }
  long long max = -1, min = __LONG_LONG_MAX__;
  for (auto const &f : frequency) {
    if (f.second < min) {
      min = f.second;
    }
    if (f.second > max) {
      max = f.second;
    }
  }
  return max - min;
}

int main() {
  auto input = readInputFile();
  cout << "part 1" << endl
       << calculate_polymer_template(input.first, input.second, 10) << endl
       << "part 2" << endl
       << calculate_polymer_template(input.first, input.second, 40) << endl;
  return 0;
}