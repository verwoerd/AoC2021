#include <algorithm>
#include <fstream>
#include <iostream>
#include <list>
#include <map>
#include <string>
#include <vector>

using namespace std;

struct variables {
  long long xAdd;
  long long zDiv;
  long long yAdd;
};

vector<variables> input;
vector<long long> zValues;
map<pair<int, long long>, vector<list<int>>> cache;

void readInputFile() {
  ifstream file("../resources/input");
  string s;
  int count = 0;
  long long xval, yval, zval;
  while (getline(file, s)) {
    if (count % 18 == 4) {
      zval = stoll(s.substr(6, s.length()));
    } else if (count % 18 == 5) {
      xval = stoll(s.substr(6, s.length()));
    } else if (count % 18 == 15) {
      yval = stoll(s.substr(6, s.length()));
      input.emplace_back(variables{xval, zval, yval});
    }
    ++count;
  }
  for (int i = 13; i >= 0; --i) {
    auto var = input[i];
    zval = var.zDiv;
    if (zValues.size() == 0) {
      zValues.emplace_back(zval);
    } else {
      zValues.emplace_back(zval * zValues.back());
    }
  }
  reverse(zValues.begin(), zValues.end());
}

long long checkDigit(long long w, long long zVal, variables vars) {
  long long x = vars.xAdd + (zVal % 26);
  long long z = zVal / vars.zDiv;
  if (x != w) {
    z = z * 26 + w + vars.yAdd;
  }
  return z;
}

vector<list<int>> smartSearch(int index, long long z) {
  auto key = pair(index, z);
  if (cache.find(pair(index, z)) != cache.end()) {
    return cache[key];
  }
  vector<list<int>> result;
  if (index == 14) {
    if (z == 0) {
      result.push_back({});
    }
  } else if (z < zValues[index]) {
    auto vars = input[index];
    for (int w = 1; w < 10; ++w) {
      auto next = checkDigit(w, z, vars);
      for (auto option : smartSearch(index + 1, next)) {
        option.push_front(w);
        result.push_back(option);
      }
    }
  }

  cache.insert(pair(key, result));
//   if (result.size() > 0) {
//     cout << key.first << "," << key.second << ": " << result.size() << endl;
//   }
  return result;
}

int main() {
  readInputFile();
  auto options = smartSearch(0, 0);
  string s;
  long long minValue  = INT64_MAX;
  long long maxValue = INT32_MIN;
  for(auto const &option: options) {
      if(option.size() == 14) {
          s = "";
          for(auto const digit: option) {
              s += '0'+ digit;
          }
          minValue = min(stoll(s), minValue);
          maxValue = max(stoll(s), maxValue);
      }
  }
    cout << "part 1" << endl
         << maxValue << endl
         << "part 2" << endl
         << minValue << endl;
  return 0;
}