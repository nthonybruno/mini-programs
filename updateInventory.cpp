// Online C++ compiler to run C++ program online
#include <iostream>
#include <vector>
#include <bits/stdc++.h>
#include <utility>
using namespace std;

/*
Compare and update the inventory stored in a 2D array against a second 2D array of a fresh delivery. Update the current existing inventory item quantities (in arr1). If an item cannot be found, add the new item and quantity into the inventory array. The returned inventory array should be in alphabetical order by item.

Find the problem statement here: https://www.freecodecamp.org/learn/coding-interview-prep/algorithms/inventory-update
*/

struct record{
    int quantity;
    string product;
};

bool compareByProductLabel(const record &a, const record &b)
{
    return a.product < b.product;
}

vector<record> updateInventory(vector<record> curInv, vector<record> newInv){
    bool newInventoryProductFound;
    
    for(int i = 0; i < newInv.size();i++){
        newInventoryProductFound = false;
        for(int j = 0; j < curInv.size();j++){
            if(newInv[i].product == curInv[j].product){
                newInventoryProductFound = true;
                curInv[j].quantity += newInv[i].quantity;
            }
        }
        
        if (!newInventoryProductFound){
            curInv.push_back(newInv[i]);
        }
    }
    sort(curInv.begin(), curInv.end(), compareByProductLabel);
    return curInv;
}


int main() {
    // Example inventory lists
    vector<record> curInv = { {21, "Bowling Ball"},{2, "Dirty Sock"},{1, "Hair Pin"},{5, "Microphone"}};

    vector<record> newInv = {{2, "Hair Pin"},{3, "Half-Eaten Apple"},{67, "Bowling Ball"},{7,"Toothpaste"}};

    vector<record> updatedInv = updateInventory(curInv,newInv);
    
    for(record i: updatedInv){
        cout << i.quantity << " " << i.product << endl;
    }
    return 0;
}