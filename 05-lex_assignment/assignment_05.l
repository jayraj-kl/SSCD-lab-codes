%{
    #include <stdio.h>
    #include <string.h>

    int ctr = 0;
    char current_type[20];                                                  // to remember the last datatype seen
    int sym_count = 0;

    struct Symbol {
        int id;
        char name[50];
        char type[20];
    } symtab[100];   // max 100 entries

    void insert_symbol(char *name, char *type) {          
        for(int i=0; i < sym_count; i++) {
            if(strcmp(symtab[i].name, name) == 0) return;                   // avoid duplicates
        }
        symtab[sym_count].id = sym_count + 1;
        strcpy(symtab[sym_count].name, name);
        strcpy(symtab[sym_count].type, type);
        sym_count++;
    }

%}

L [a-zA-Z]
D [0-9]

%%
^#include.*                 { printf("Header Detected: %s\n", yytext); ctr++; }
int|float|char|double       { printf("%s Datatype Detected\n", yytext); strcpy(current_type, yytext); ctr++; }

{L}({L}|{D})*\(             {
                                char temp[50];
                                strncpy(temp, yytext, strlen(yytext)-1); // remove '('
                                temp[strlen(yytext)-1] = '\0';
                                printf("%s Function Detected\n", temp);
                                insert_symbol(temp, "function");
                                ctr++;
                            }

{L}({L}|{D})*\[             {
                                char temp[50];
                                strncpy(temp, yytext, strlen(yytext)-1); // remove '['
                                temp[strlen(yytext)-1] = '\0';
                                printf("%s Array Detected\n", temp);
                                insert_symbol(temp, "array");
                                ctr++;
                            }

{D}+                        { printf("%s Number Detected\n", yytext); ctr++; }
if                          { printf("IF keyword\n"); insert_symbol(yytext, "keyword"); ctr++; }
printf                      { printf("printf keyword\n"); insert_symbol(yytext, "keyword"); ctr++; }
{L}({L}|{D})*               { printf("%s Identifier Detected\n", yytext); insert_symbol(yytext, "identifier"); ctr++; }
;                           { printf("%s End of Statement Detected\n", yytext); ctr++; }
=                           { printf("%s Assignment Operator Detected\n", yytext); ctr++; }
[+\-*/]                     { printf("%s Arithmetic Operator Detected\n", yytext); ctr++; }
\[                          { printf("%s Left Bracket Detected\n", yytext); ctr++; }
\]                          { printf("%s Right Bracket Detected\n", yytext); ctr++; }
[<>=!]=?                    { printf("%s Relational Operator Detected\n", yytext); ctr++; }
&&|\\|!                     { printf("%s Logical Operator Detected\n", yytext); ctr++; }
\(                          { printf("%s Left Parenthesis Detected\n", yytext); ctr++; }
\)                          { printf("%s Right Parenthesis Detected\n", yytext); ctr++; }
,                           { printf("%s Comma Detected\n", yytext); ctr++; }
\{                          { printf("%s Left Brace Detected\n", yytext); ctr++; }
\}                          { printf("%s Right Brace Detected\n", yytext); ctr++; }
[ \t\n\r]+                  { /* ignore whitespace including CRLF */ }

.                           { printf("%s Unknown Token\n", yytext); }

%%
int main() {
    yyin = fopen("sample.txt","r");
    yylex();

    printf("\n--- SYMBOL TABLE ---\n");
    printf("ID\tName\tType\n");
    printf("-------------------------\n");
    
    for(int i = 0; i < sym_count; i++) {
        printf("%d\t%s\t%s\n", symtab[i].id, symtab[i].name, symtab[i].type);
    }

    printf("\nTotal Tokens = %d\n", ctr);
    return 0;
}

int yywrap() {
    return 1;
}