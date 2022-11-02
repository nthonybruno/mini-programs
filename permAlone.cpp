// Online C++ compiler to run C++ program online
#include <iostream>
#include <vector>
#include <bits/stdc++.h>
#include <utility>
using namespace std;

/*
Return the number of total permutations of the provided string that don't have repeated consecutive letters. Assume that all characters in the provided string are each unique.

For example, aab should return 2 because it has 6 total permutations (aab, aab, aba, aba, baa, baa), but only 2 of them (aba and aba) don't have the same letter (in this case a) repeating.
*/
vector<string> permutations;

void permute(string &a, int l, int r)
{
    // Base case
    if (l == r)
        permutations.push_back(a);
    else
    {
        // Permutations made
        for (int i = l; i <= r; i++)
        {
            // Swapping done
            swap(a[l], a[i]);
            // Recursion called
            permute(a, l+1, r);
            //backtrack
            swap(a[l], a[i]);
        }
    }
}

int permAlone(string s){
    permute(s, 0, s.size()-1);
    int noRepeatedConsecutiveLetters = 0;
    char prevChar;
    bool currPermHasNoRepeatedConsecutiveChars;
    
    for(int i = 0; i < permutations.size();i++){
        currPermHasNoRepeatedConsecutiveChars = true;
        for(int j = 0; j < permutations[i].size();j++){
            if(j==0){
                prevChar = permutations[i][j];
            } else {
                if (permutations[i][j] == prevChar){
                    currPermHasNoRepeatedConsecutiveChars = false;
                    break;
                }
            }
        }
        if(currPermHasNoRepeatedConsecutiveChars){
            noRepeatedConsecutiveLetters++;
        }
    }
    return noRepeatedConsecutiveLetters;
}

int main() {
    // Write C++ code here
    string s = "aab";
    cout << permAlone(s);
    
    /*for(string s: permutations){
        cout << s << endl;
    }*/

    return 0;
}