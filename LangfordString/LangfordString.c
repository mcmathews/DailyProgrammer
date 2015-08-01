#include <stdio.h>
#include <stdlib.h>

int g = 0;

struct CharNode
{
	char c;
	CharNode * next;
	CharNode * prev;
};

CharNode * head;

void setChar(CharNode * node, char c) {
	node->c = c;
	if (node->prev != NULL) {
		node->prev->next = node->next;
	}

	if (node->next != NULL) {
		node->next->prev = node->prev;
	}
}

// TODO: Implement
void unsetChar(CharNode * node, CharNode * after) {

}

void build(char * ls, char * uc, int i, int length, char lastChar) {
	printf(" -- Maybe -- %s\n", ls);

	if (c > lastChar) {
		g++;
		printf("%s\n", ls);
		if (g > 20) {
			exit(0);
		}
	}

	int i;
	int offset = c - 'A' + 2;
	for (char c = 'A'; c < lastChar; c++) {
		if (ls[i] == ' ' && =ls[i + offset] == ' ') {
			ls[i] = ls[i + offset] = c;
			build(ls, c + 1, length, lastChar);
			ls[i] = ls[i + offset] = ' ';
		}
	}
}

int main(int argc, char const *argv[]) {
	if (argc < 2) {
		// TODO fix it
		printf("Fuck you\n");
		return 1;
	}

	int n = atoi(argv[1]);
	char lastChar = 'A' + n - 1;
	char * ls = malloc((2 * n) + 1);
	char * uc = malloc(n);
	int i = 0;
	for (; i < n * 2; i++) {
		ls[i] = ' ';
	}
	for (i = 0; i < n; i++) {
		uc[i] = 0
	}
	ls[2*n] = 0;

	build(ls, uc, 'A', 2*n, lastChar);
	free(ls);
	free(uc);
	return 0;
}