#include <iostream>
#include <fstream>
#include <vector>
#include <string>
#include <algorithm>
#include <cmath>

#define VLL vector<long long>

using namespace std;

VLL readInputFile() {
    ifstream file("../resources/input");
    VLL lines;
    string s;
    while(getline(file, s)) {
        lines.push_back(stoll(s,0,2));
    }
    return lines;
}

int runPowerDiagnostics(VLL output) {
    int gamma =  0;
    int epsilon = 0;
    int count = 0;
    int highest = (int)(log2(*max_element(output.begin(), output.end())));
    for(int i = 0; i <= highest; i++) {
        count = 0;
        for (auto const &line: output) {
            count += (line >> i) & 1;
        }

        if(count * 2 > output.size()) {
            gamma += (1 << i);
        } else {
            epsilon += (1 << i);
        }
    }
    return gamma * epsilon;
}

int findOutputLine(VLL output, int lsb ) {
 int highest = (int)(log2(*max_element(output.begin(), output.end())));
 int toAdd = 0;
 VLL filtered = output;
 while(filtered.size() > 1) {
     int count = 0;
    for (auto const &line: filtered) {
        count += (line >> highest) & 1;
    }
    if(count * 2 >= filtered.size()) {
        toAdd = lsb;
    } else {
        toAdd = 1 - lsb;
    }
    VLL nextIteration;
    for(auto const &line: filtered) {
        if(((line >> highest) & 1) == toAdd) {
            nextIteration.push_back(line);
        }
    }
    filtered = nextIteration;
    highest--;
 }
 return filtered[0];
}


int runOxygenDiagnostic(VLL output) {
    int oxygen = findOutputLine(output, 1);
    int co2 = findOutputLine(output, 0);
    return oxygen * co2;
}


int main() {
    VLL input = readInputFile();
    cout << "part 1" << endl
         << runPowerDiagnostics(input) << endl
         << "part 2" << endl
         << runOxygenDiagnostic(input) << endl;
    return 0;
}
