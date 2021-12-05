#include <fstream>
#include <iostream>
#include <set>
#include <sstream>
#include <string>
#include <vector>

using namespace std;
#define CARD vector<vector<int>>
#define VI vector<int>
#define CARDS vector<CARD>

vector<int> parseLine(string line) {
  vector<int> result;
  stringstream ss(line);
  for (int i; ss >> i;) {
    result.push_back(i);
    if (ss.peek() == ' ')
      ss.ignore();
  }
  return result;
}

pair<VI, CARDS> readInputFile() {
  ifstream file("../resources/input");
  string s;
  getline(file, s);
  stringstream ss(s);
  VI numbers;
  for (int i; ss >> i;) {
    numbers.push_back(i);
    if (ss.peek() == ',')
      ss.ignore();
  }
  CARDS cards;
  CARD current;
  VI line;
  getline(file, s);
  while (getline(file, s)) {
    if (s == "") {
      cards.push_back(current);
      current = CARD();
      continue;
    }
    current.push_back(parseLine(s));
  }
  cards.push_back(current);
  return pair(numbers, cards);
}

bool markNumber(CARD &card, int number) {
  for (size_t y = 0; y < 5; y++) {
    for (size_t x = 0; x < 5; x++) {
      if (card[y][x] == number) {
        card[y][x] = -1;
        bool xbingo = true;
        bool ybingo = true;
        for (size_t i = 0; i < 5; i++) {
          xbingo &= card[y][i] < 0;
          ybingo &= card[i][x] < 0;
        }
        return xbingo || ybingo;
      }
    }
  }
  return false;
}

vector<pair<int, CARD>> playBingo(VI numbers, CARDS cards) {
  vector<pair<int, CARD>> result;
  set<int> seen;
  for (auto const &number : numbers) {
    for (size_t i = 0; i < cards.size(); i++) {
      if (seen.find(i) != seen.end())
        continue;
      if (markNumber(cards[i], number)) {
        result.push_back(pair(number, cards[i]));
        seen.insert(i);
      }
    }
  }
  return result;
}

int calculateResult(pair<int, CARD> combo) {
  int result = 0;
  for (auto const &line : combo.second) {
    for (auto const &number : line) {
      if (number > 0)
        result += number;
    }
  }
  return result*combo.first;
}

int main() {
  pair<VI, CARDS> result = readInputFile();
  VI numbers = result.first;
  CARDS cards = result.second;
  vector<pair<int, CARD>> results = playBingo(numbers, cards);
  cout << "part 1" << endl
       << calculateResult(*results.begin()) << endl
       << "part 2" << endl
       << calculateResult(*results.rbegin()) << endl;

  return 0;
}