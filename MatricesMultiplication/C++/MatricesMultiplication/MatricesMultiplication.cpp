#include "stdafx.h"

#include <vector>
#include <iostream>
#include <iomanip>
#include <time.h>

using namespace std;

// generates random float number
inline float random_float() {
	const float LO = -200;
	const float HI = +200;

	return LO + static_cast <float> (rand()) /( static_cast <float> (RAND_MAX/(HI-LO)));
}

// generates new matrix (with proper memmory allocations) of given size with random content
vector<vector<float>> generate_matrix(int m, int n) {
	vector<vector<float>> matrix(m, vector<float>(n));

	for (auto&& row : matrix)
		for (int j = 0; j < n; j++)
			row[j] = random_float();
    return matrix;
}

// pretty print matrix
void print_matrix(vector<vector<float>> matrix) {
	if (matrix.size() > 10 || matrix[0].size() > 10)
		return; // avoid printing large matrices :)

	for (auto&& row : matrix) {
		for (auto&& item : row)
			cout << setw(10) << item;
		cout << endl;
	}
	cout << endl;
}

// multiply two matrices using standard ikj-algorithm and store result in new one
vector<vector<float>> multiply(vector<vector<float>> &m1, vector<vector<float>> &m2) {
	int a = m1.size();
	int c = m2.size();
	int d = m2[0].size();

	vector<vector<float>> result(a, vector<float>(d, 0));
    for (int i = 0; i < a; i++)
        for (int k = 0; k < c; k++) {
            for (int j = 0; j < d; j++)
                result[i][j] += m1[i][k] * m2[k][j];
        }
	return result;
}

int main() {
    int m = 2000, n = 2000;
    srand(time(NULL));

	vector<vector<float>> a = generate_matrix(m, n);
	vector<vector<float>> b = generate_matrix(n, m);

    print_matrix(a);
    print_matrix(b);

	clock_t begin = clock();
	vector<vector<float>> result = multiply(a, b);
	clock_t end = clock();

    print_matrix(result);
	cout << endl << (double)(end - begin) / CLOCKS_PER_SEC << endl;

	// just for VS users, wait before closing terminal
	getchar(); 

    return 0;
}
