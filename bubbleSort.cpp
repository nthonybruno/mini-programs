#include <iostream>
#include <vector>
#include <bits/stdc++.h>
using namespace std;
/*
Bubble sort (least to greatest)

https://www.freecodecamp.org/learn/coding-interview-prep/algorithms/implement-bubble-sort
*/

vector<int> bubbleSort(vector<int> arr){
    if(arr.size()==1){
        return arr;
    }
    bool notSorted = true;
    bool noSwapsMade;
    
    while(notSorted){
        noSwapsMade = true;
        for(int i = 0; i < arr.size()-1;i++){
            if(arr[i]>arr[i+1]){
                noSwapsMade = false;
                swap(arr[i],arr[i+1]);
            }
        }
        if(noSwapsMade){
            notSorted = false;
        }
    }
    return arr;
}

int main() {
    //vector<int> arr = {2,6,1,7,2};
    vector<int> arr = {1,4,2,8,345,123,43,32,5643,63,123,43,2,55,1,234,92};
    vector<int> sortedArr = bubbleSort(arr);
    for(int i: sortedArr){
        cout << i << " ";
    }
    return 0;
}