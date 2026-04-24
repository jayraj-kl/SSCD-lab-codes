%{
#include "y.tab.h"
#include <stdlib.h>
%}

%%
[0-9]+      { yylval = atoi(yytext); return NUMBER; }
[ \t]       { /* skip whitespace */ }
\n          { return 0; /* Logical EOF for a single calculation */ }
.           { return yytext[0]; }
%%

int yywrap() {
    return 1;
}