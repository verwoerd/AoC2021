#include <fstream>
#include <iostream>
#include <map>
#include <queue>
#include <set>
#include <string>
#include <vector>

using namespace std;

struct entry {
  string node;
  set<string> seen;
  bool doubled;
};

map<string, vector<string>> readInputFile() {
  ifstream file("../resources/input");
  map<string, vector<string>> routes;
  string s, from, to;
  int delim;
  while (getline(file, s)) {
    delim = s.find("-");
    from = s.substr(0, delim);
    to = s.substr(delim + 1, s.length());
    routes.emplace(from, vector<string>());
    routes.emplace(to, vector<string>());
    routes[from].push_back(to);
    routes[to].push_back(from);
  }
  return routes;
}

int countPossibleRoutes(map<string, vector<string>> &routes,
                        bool doubleRule = false) {
  queue<entry> q;
  q.push({"start", set<string>(), false});
  int count = 0;
  while (!q.empty()) {
    auto current = q.front();
    q.pop();
    for (auto const &option : routes[current.node]) {
      if (option == "end") {
        ++count;
      } else if (option == "start") {
        continue;
      } else if (option[0] >= 'a' && option[0] <= 'z') {
        if (current.seen.find(option) == current.seen.end()) {
          auto newSeen = current.seen;
          newSeen.insert(option);
          q.push({option, newSeen, current.doubled});
        } else if (doubleRule && !current.doubled) {
          q.push({option, current.seen, true});
        }
      } else {
        q.push({option, current.seen, current.doubled});
      }
    }
  }
  return count;
}

int main() {
  auto routes = readInputFile();
  cout << "part 1" << endl
       << countPossibleRoutes(routes) << endl
       << "part 2" << endl
       << countPossibleRoutes(routes, true) << endl;
  return 0;
}