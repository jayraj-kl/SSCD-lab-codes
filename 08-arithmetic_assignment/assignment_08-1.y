%{
#include <stdio.h>
#include <stdlib.h>
#include <ctype.h> 


int yylex(void);
void yyerror(char const *s);
%}


%token NUMBER


%left '+' '-'
%left '*' '/'

%%
input:
    | input line
    ;

line:
    '\n'
    | exp '\n'  { printf("Result: %d\n", $1); }
    ;

exp:
    NUMBER          { $$ = $1; }
    | exp '+' exp   { $$ = $1 + $3; }
    | exp '-' exp   { $$ = $1 - $3; }
    | exp '*' exp   { $$ = $1 * $3; }
    | exp '/' exp   { 
                        if ($3 == 0) {
                            yyerror("Divide by zero!");
                            $$ = 0;
                        } else {
                            $$ = $1 / $3; 
                        }
                    }
    | '(' exp ')'   { $$ = $2; }
    ;

%%


int yylex(void) {
    int c;

    /* Skip white spaces and tabs */
    while ((c = getchar()) == ' ' || c == '\t') {
        continue;
    }

    /* If it's a number, read the whole integer */
    if (isdigit(c)) {
        ungetc(c, stdin);      /* Put the first digit back */
        scanf("%d", &yylval);  /* Read the full integer into yylval */
        return NUMBER;
    }


    if (c == EOF) {
        return 0;
    }

    return c;
}

void yyerror(char const *s) {
    fprintf(stderr, "Error: %s\n", s);
}

int main(void) {
    printf("Enter expression (e.g., 2+3*4) and press Enter:\n");
    yyparse();
    return 0;
}
