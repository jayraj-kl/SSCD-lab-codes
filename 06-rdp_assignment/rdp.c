#include<stdio.h>
#include<ctype.h>

char input[100];
int terminal_counter = 0;
int error = 0;

void E() {
	printf("\nE -> TE1");

	T();
	E1();
}

void T() {
	printf("\nT -> FT1");

	F();
	T1();	
}

void E1() {	 
	if(input[terminal_counter] == '+') {
		terminal_counter++;
		printf("\nE1 -> +TE1");
		
		T();
		E1();
	} else {
		if(input[terminal_counter] == '-') {
			terminal_counter++;
			printf("\nE1 -> -TE1");

			T();
			E1();
		}
	}
}


void T1(){
	if(input[terminal_counter] == '*') {
		terminal_counter++;
		printf("\nT1 -> *FT1");

		F();
		T1();
	} else {
		if(input[terminal_counter] == '/') {
			terminal_counter++;
			printf("\nE1 -> /TE1");

			T();
			E1();
		}
    }
}

void F() {
	if(input[terminal_counter] == '(') {
		terminal_counter++;
		printf("\n F -> (E)");
		
		E(); 

		if(input[terminal_counter] == ')') {
			terminal_counter++;
		}
		else {
			error = 1;
		}
	}
	else {
		printf("\nF -> id");
		if(isalnum(input[terminal_counter])) {
			terminal_counter++;
		}	
		else {
			error = 1;
		}
	}
}


int main() {
	printf("Enter The string :");
	scanf("%s",input);

	E();
	
	if(input[terminal_counter] == '\0' && error == 0) {
		printf("\n\nString accepted.\n");
	} else {
		printf("\n\nString is not accepted.\n");
	}
    return 0;
}