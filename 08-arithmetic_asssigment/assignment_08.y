%{
#include <stdio.h>
#include <stdlib.h>

extern int yylex();
extern int yywrap();
%}

%token NUMBER
%left '+' '-'
%left '*' '/'

%%
/* Grammar Rules */
calculation:
    expression          { printf("Result: %d\n", $$); } 
    ;

expression:
      expression '+' expression { $$ = $1 + $3; } 
    | expression '-' expression { $$ = $1 - $3; }
    | expression '*' expression { $$ = $1 * $3; }
    | expression '/' expression {  if($3 == 0) { printf("Divide by zero"); exit(1); } else $$ = $1 / $3;  }
    | '(' expression ')'        { $$ = $2; }
    | NUMBER                    { $$ = $1; }
    ;
%%

int yyerror (char *str) {
    fprintf(stderr,"error: %s\n",str);
    return 0;
}

int main() {
    printf("Enter an expression (e.g., 4 + 5 * 2):\n");
    yyparse();
    return 0;
}