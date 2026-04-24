%{
#include "y.tab.h"
#include <stdlib.h>
%}

%%
"if"                            { printf("TOKEN RECOGZNIZED `if`: %s \n", yytext); return IF; }
"then"                          { printf("TOKEN RECOGZNIZED `then`: %s \n", yytext); return THEN; }

"<"                             { printf("TOKEN RECOGZNIZED ROP: %s \n", yytext); return ROP; }
"="                             { printf("TOKEN RECOGZNIZED AOP: %s \n", yytext); return AOP; }

[0-9]+                          { printf("TOKEN RECOGZNIZED NUM: %s \n", yytext); return NUM; }
([a-zA-Z])("_"|[a-zA-Z0-9])*    { printf("TOKEN RECOGZNIZED ID: %s \n", yytext); return ID; }
%%

int yywrap() {
    return 1;
}