YACC=yacc
LEX=lex
CC=gcc

YACC_SRC=assignment_08.y
LEX_SRC=assignment_08.l

PARSER_C=y.tab.c
PARSER_H=y.tab.h
SCANNER_C=lex.yy.c
OUT=calculator

.PHONY: all run clean

all: $(OUT)

$(OUT): $(YACC_SRC) $(LEX_SRC)							
	$(YACC) -d -v $(YACC_SRC)								
	$(LEX) $(LEX_SRC)													
	$(CC) $(PARSER_C) $(SCANNER_C) -o $(OUT) -lfl		

run: all
	./$(OUT)											

clean:
	rm -f $(PARSER_C) $(PARSER_H) $(SCANNER_C) $(OUT)	