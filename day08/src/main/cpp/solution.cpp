#include <algorithm>
#include <cstring>
#include <fstream>
#include <iostream>
#include <iterator>
#include <sstream>
#include <string>
#include <vector>

using namespace std;

#define PVSVS pair<vector<string>, vector<string>>

class Digit {
private:
  char top;
  char middle;
  char bottom;
  char topLeft;
  char topRight;
  char bottomLeft;
  char bottomRight;

public:
  void solve(vector<string> options) {
    string one = *find_if(options.begin(), options.end(),
                          [](string l) { return l.length() == 2; });
    string seven = *find_if(options.begin(), options.end(),
                            [](string l) { return l.length() == 3; });
    string four = *find_if(options.begin(), options.end(),
                           [](string l) { return l.length() == 4; });
    string eight = *find_if(options.begin(), options.end(),
                            [](string l) { return l.length() == 7; });
    top = *find_if(seven.begin(), seven.end(),
                   [one](char l) { return one.find(l) == string::npos; });
    string six = *find_if(options.begin(), options.end(), [one](string s) {
      if (s.length() != 6)
        return false;
      int count = 0;
      for (auto const &f : one) {
        count += s.find(f) == string::npos;
      }
      return count == 1;
    });
    string nine = *find_if(options.begin(), options.end(), [four](string s) {
      if (s.length() != 6)
        return false;
      bool found = true;
      for (auto const &f : four) {
        found &= s.find(f) != string::npos;
      }
      return found;
    });
    topRight = *find_if(eight.begin(), eight.end(),
                        [six](char c) { return six.find(c) == string::npos; });
    bottomLeft = *find_if(eight.begin(), eight.end(), [nine](char c) {
      return nine.find(c) == string::npos;
    });
    bottomRight =
        *find_if(one.begin(), one.end(), [&](char c) { return c != topRight; });
    bottom = *find_if(nine.begin(), nine.end(), [&, four](char c) {
      return four.find(c) == string::npos && c != top;
    });
    string zero =
        *find_if(options.begin(), options.end(), [six, nine](string s) {
          return s.length() == 6 && s != six && s != nine;
        });
    topLeft = *find_if(zero.begin(), zero.end(), [&](char c) {
      return c != top && c != bottom && c != topRight && c != bottomRight &&
             c != bottomLeft;
    });
    middle = *find_if(four.begin(), four.end(), [&](char c) {
      return c != topLeft && c != topRight && c != bottomRight;
    });
  }
  int parseDisplay(vector<string> display) {
    int count = 0;
    for (auto const &digit : display) {
      count = 10 * count + parseDigit(digit);
    }
    return count;
  }

  int parseDigit(string digit) {
    switch (digit.size()) {
    case 2:
      return 1;
    case 3:
      return 7;
    case 4:
      return 4;
    case 5:
      if (digit.find(bottomRight) == string::npos) {
        return 2;
      } else if (digit.find(topLeft) == string::npos) {
        return 3;
      } else {
        return 5;
      }
    case 6:
      if (digit.find(middle) == string::npos) {
        return 0;
      } else if (digit.find(bottomLeft) == string::npos) {
        return 9;
      } else {
        return 6;
      }
    case 7:
      return 8;
    default:
      throw 42;
    }
  }
};

vector<PVSVS> readInputFile() {
  ifstream file("../resources/input");
  vector<PVSVS> result;
  string s;
  while (getline(file, s)) {
    vector<string> digits;
    vector<string> display;
    bool isDigits = true;
    stringstream ss(s);
    for (string word; ss >> word;) {
      if (word == "|") {
        isDigits = false;
        continue;
      }
      if (isDigits) {
        digits.push_back(word);
      } else {
        display.push_back(word);
      }
    }
    result.push_back(pair(digits, display));
  }
  return result;
}

int countSimpleDigits(vector<PVSVS> input) {
  int count = 0;
  for (auto const &value : input) {
    for (auto const &displays : value.second) {
      if ((displays.size() >= 2 && displays.size() <= 4) ||
          displays.size() == 7) {
        count++;
      }
    }
  }
  return count;
}

int countDisplayDigits(vector<PVSVS> input) {
  int count = 0;
  for (auto const &value : input) {
    Digit digit;
    digit.solve(value.first);
    count += digit.parseDisplay(value.second);
  }
  return count;
}

int main() {
  vector<PVSVS> input = readInputFile();
  cout << "part 1" << endl
       << countSimpleDigits(input) << endl
       << "part 2" << endl
       << countDisplayDigits(input) << endl;
  return 0;
}