#include <iostream>
#include <vector>
#include <bits/stdc++.h>
using namespace std;
/*
Selection Sort (least to greatest)

https://www.freecodecamp.org/learn/coding-interview-prep/algorithms/implement-selection-sort

Selection sort works by selecting the minimum value in a list and swapping it with the first value in the list. It then starts at the second position, selects the smallest value in the remaining list, and swaps it with the second element. It continues iterating through the list and swapping elements until it reaches the end of the list. Now the list is sorted. Selection sort has quadratic time complexity in all cases.
*/

int indexOfMinElement(vector<int>arr, int start){
    int min = start;
    for(int i = start+1; i < arr.size();i++){
        if (arr[i]<arr[min]){
            min = i;
        }
    }
    return min;
}

vector<int> selectionSort(vector<int> arr,int pivot){
    if(pivot == arr.size()-1){
        return arr;
    }
    int min = indexOfMinElement(arr, pivot);
    swap(arr[pivot], arr[min]);
    return selectionSort(arr, ++pivot);
}

int main() {
    //vector<int> arr = {2,6,1,7,2};
    vector<int> arr = {1,4,2,8,345,123,43,32,5643,63,123,43,2,55,1,234,92};
    vector<int> sortedArr = selectionSort(arr,0);
    for(int i: sortedArr){
        cout << i << " ";
    }
    return 0;
}