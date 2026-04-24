%{
#include <stdio.h>
#include <stdlib.h>

extern int yylex();
extern int yywrap();
%}

%token IF THEN ROP AOP NUM ID

%%
start: sif                          {printf("VALID STATEMENT START: IT's a SYNTACTICALLY CORRECT STATEMENT \n");};
sif: IF cmpn THEN stmt              {printf("VALID STATEMENT IF \n");};
cmpn: ID ROP ID                     {printf("VALID STATEMENT CMPN \n");};
stmt: ID AOP NUM                    {printf("VALID STATEMENT STMT \n");};
%%

int yyerror (char *str) {
    fprintf(stderr,"error: %s\n",str);
    return 0;
}

int main() {
    printf("Enter an expression :\n");
    yyparse();
    return 0;
}