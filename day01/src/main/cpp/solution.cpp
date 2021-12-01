#include <iostream>
#include <fstream>
#include <vector>
#include <string>

#define VLL vector<long long>

using namespace std;

VLL readInputFile() {
    ifstream file("../resources/input");
    VLL lines;
    string s;
    while(getline(file, s)) {
        lines.push_back(stoll(s));
    }
    return lines;
}

int countDeeperLines(VLL sonar, int diff) {
    int count = 0;
    for(int i = 0; i < sonar.size() - diff; i++){
        if(sonar[i+diff] > sonar[i]) count++;
    }
    return count;
}

int main() {
    VLL sonar = readInputFile();
    cout << "part 1" << endl;
    cout << countDeeperLines(sonar, 1) << endl;
    cout << "part 2" << endl;
    cout << countDeeperLines(sonar, 3) << endl;
    return 0;
}
