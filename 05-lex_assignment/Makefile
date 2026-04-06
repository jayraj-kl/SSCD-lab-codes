LEX=flex
CC=gcc
SRC=assignment_05.l
LEX_C=lex.yy.c
OUT=lexer

.PHONY: all run clean

all: $(OUT)

$(OUT): $(SRC)
	$(LEX) $(SRC)
	$(CC) $(LEX_C) -o $(OUT) -lfl

run: all
	./$(OUT)

clean:
	rm -f $(LEX_C) $(OUT)
