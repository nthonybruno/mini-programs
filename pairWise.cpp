#include <iostream>
#include <vector>
#include <bits/stdc++.h>
using namespace std;

/*
Pairwise
Given an array arr, find element pairs whose sum equal the second argument arg and return the sum of their indices.

For example pairwise([7, 9, 11, 13, 15], 20) returns 6. The pairs that sum to 20 are [7, 13] and [9, 11]. We can then write out the array with their indices and values.

https://www.freecodecamp.org/learn/coding-interview-prep/algorithms/pairwise
*/

int pairWise(vector<int> arr, int target){
    int sumOfPairs = 0;
    //vector<int> used(arr.size(),0);
    //vector<int> pairIndices;
    for(int i = 0; i < arr.size()-1; i++){
        for(int j = i+1; j < arr.size(); j++){
            if(arr[i]+arr[j] == target && (arr[i] != -1 && arr[j] != -1)){
                sumOfPairs += (i + j);
                arr[i] = -1;
                arr[j] = -1;
            }
        }
    }
    return sumOfPairs;
}

int main() {
    vector<int> arrA = {1,1,2};
    vector<int> arrB = {7,9,11,13,15};
    vector<int> arrC = {1,4,2,3,0,5};
    vector<int> arrD = {1,3,2,4};
    cout << pairWise(arrA, 3) << endl;
    cout << pairWise(arrB, 20) << endl;
    cout << pairWise(arrC, 7) << endl;
    cout << pairWise(arrD, 4) << endl;
    cout << pairWise({1,1,1}, 2) << endl;
    vector<int> arrE = {};
    cout << pairWise(arrE, 100) << endl;
    return 0;
}