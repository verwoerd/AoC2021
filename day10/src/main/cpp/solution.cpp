#include <algorithm>
#include <fstream>
#include <iostream>
#include <set>
#include <stack>
#include <string>
#include <vector>

using namespace std;

#define compilerResult pair<char, stack<char>>

const set<char> openingChars = {'(', '[', '<', '{'};

char closingChar(char c) {
  switch (c) {
  case '(':
    return ')';
  case '[':
    return ']';
  case '<':
    return '>';
  case '{':
    return '}';
  default:
    throw 1;
  }
}

compilerResult compileLine(string line) {
  char invalid = ' ';
  stack<char> openend;
  for (auto const &c : line) {
    if (openingChars.find(c) != openingChars.end()) {
      openend.push(c);
    } else if (closingChar(openend.top()) == c) {
      openend.pop();
    } else {
      invalid = c;
      break;
    }
  }
  return pair(invalid, openend);
}

vector<compilerResult> readInput() {
  ifstream file("../resources/input");
  vector<compilerResult> result;
  string s;
  while (getline(file, s)) {
    result.push_back(compileLine(s));
  }
  return result;
}

long long calculateSyntaxErrorScore(vector<compilerResult> problems) {
  long long count = 0;
  for (auto const &problem : problems) {
    switch (problem.first) {
    case ')':
      count += 3;
      break;
    case ']':
      count += 57;
      break;
    case '}':
      count += 1197;
      break;
    case '>':
      count += 25137;
      break;
    default:
      continue;
    }
  }
  return count;
}

long long calculateMissingScore(vector<compilerResult> problems) {
  vector<long long> score;
  for (auto const &problem : problems) {
    if (problem.first != ' ') {
      continue;
    }
    stack<char> charStack = problem.second;
    long long count = 0;
    while (!charStack.empty()) {
      count *= 5;
      switch (charStack.top()) {
      case '(':
        count += 1;
        break;
      case '[':
        count += 2;
        break;
      case '{':
        count += 3;
        break;
      case '<':
        count += 4;
        break;
      default:
        throw 2;
      }
      charStack.pop();
    }
    score.push_back(count);
    
  }
  sort(score.begin(), score.end());
  return score[score.size() / 2];
}

int main() {
  vector<compilerResult> problems = readInput();
  cout << "part 1" << endl
       << calculateSyntaxErrorScore(problems) << endl
       << "part 2" << endl
       << calculateMissingScore(problems) << endl;
  return 0;
}