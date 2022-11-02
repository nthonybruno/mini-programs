// Online C++ compiler to run C++ program online
#include <iostream>
#include <vector>
#include <bits/stdc++.h>
using namespace std;

/*
Create a function that takes two or more arrays and returns an array of their symmetric difference. The returned array must contain only unique values (no duplicates).

Find the problem statement here: https://www.freecodecamp.org/learn/coding-interview-prep/algorithms/find-the-symmetric-difference
*/

vector<int> sym(vector<int> listA, vector<int> listB){
    int listASize = listA.size();
    int listBSize = listB.size();
    int maxSize = max(listASize,listBSize);
    
    int maxElemA = *max_element(listA.begin(),listA.end())+1;
    int maxElemB = *max_element(listB.begin(),listB.end())+1;
    int maxElem = max(maxElemA, maxElemB);
    vector<int> differenceArr;

    vector<int> freqArrA(maxElem,0);
    vector<int> freqArrB(maxElem,0);

    for(int i = 0; i < maxSize;i++){
        if(i < listASize && i < listBSize){
            freqArrA[listA[i]]++;
            freqArrB[listB[i]]++;
        } else if (i < listASize){
            freqArrA[listA[i]]++;
        } else if(i < listBSize){
            freqArrB[listB[i]]++;
        }
    }
    
    for(int i = 0; i < maxElem; i++){
        if( (freqArrA[i] > 0 && freqArrB[i] == 0) || (freqArrA[i] == 0 && freqArrB[i] > 0)){
            differenceArr.push_back(i);
        }
    }
    return differenceArr;
}

int main() {
    vector<int> freq = sym({3,3,3,2,5},{2,1,5,7});
    for(int i: freq){
        cout << i << " ";
    }
    cout << endl;
    
    freq = sym({3,3,3,2,5},{2,1,5,7});
    for(int i: freq){
        cout << i << " ";
    }
    cout << endl;
    
    freq = sym(sym(sym(sym(sym({3,3,3,2,5},{2,1,5,7}),{3,4,6,6}),{1,2,3}),{5,3,9,8}),{1});
    for(int i: freq){
        cout << i << " ";
    }
    cout << endl;
    return 0;
}