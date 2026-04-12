#include<stdio.h>
#include<ctype.h>

char input[100];
int i=0;
int error=0;
void E();
void E1();
void T();
void T1();
void F();

int main() {
	printf("Enter The string :");
	scanf("%s",input);
	E();
	if(input[i]=='\0' && error==0) {
		printf("\n\nString accepted.");
	} else {
		printf("\n\nString is not accepted.");
	}
    return 0;
}



void E() {
	printf("\nE->TE1");
	T();
	E1();
}

void T() {
	printf("\nT-> FT1");
	F();
	T1();	
}

void E1() {	 
	if(input[i]=='+') {
		i++;
		printf("\nE1 -> +TE1");
		T();
		E1();
	} else {
		if(input[i]=='-') {
			i++;
			printf("\nE1 -> -TE1");
			T();
			E1();
		}
	}
}


void T1(){
	if(input[i]=='*') {
		i++;
		printf("\nT1 -> *FT1");
		F();
		T1();
	} else {
		if(input[i]=='/') {
			i++;
			printf("\nE1 -> /TE1");
			T();
			E1();
		}
    }
}

void F() {
	if(input[i]=='(') {
		printf("\n F -> (E)");
		i++;
		E();
		if(input[i]== ')') {
			i++;
		}
		else {
			error=1;
		}
	}
	else {
	printf("\nF -> id");
		if(isalnum(input[i])) {
			i++;
		}	
		else {
			error=1;
		}
	}
}


/*
OUTPUT:-

[student@localhost ~]$ gcc rdp1.c
[student@localhost ~]$ ./a.out
Enter The string :a+b

E->TE1
T-> FT1
F -> id
E1 -> +TE1
T-> FT1
F -> id

String accepted.[student@localhost ~]$ ./a.out
Enter The string :a+b/c*d-s

E->TE1
T-> FT1
F -> id
E1 -> +TE1
T-> FT1
F -> id
E1 -> /TE1
T-> FT1
F -> id
T1 -> *FT1
F -> id
E1 -> -TE1
T-> FT1
F -> id

String accepted.[student@localhost ~]$ ./a.out
Enter The string :a+b;

E->TE1
T-> FT1
F -> id
E1 -> +TE1
T-> FT1
F -> id

[student@localhost ~]$ 

*/

