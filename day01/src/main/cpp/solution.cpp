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

int countDeeperLines(VLL sonar) {
    int count = 0;
    for(int i = 0; i < sonar.size() - 1; i++){
        if(sonar[i+1] > sonar[i]) count++;
    }
    return count;
}

int main() {
    VLL sonar = readInputFile();
    cout << "part 1" << endl;
    cout << countDeeperLines(sonar) << endl;
    cout << "part 2" << endl;
    VLL windowed;
    for(int i=0;  i < sonar.size() - 2; i++) {
        windowed.push_back(sonar[i]+sonar[i+1]+sonar[i+2]);
    }
    cout << countDeeperLines(windowed) << endl;
    return 0;
}
