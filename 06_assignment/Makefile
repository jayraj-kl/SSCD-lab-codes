CC=gcc
CFLAGS=-Wall -Wextra -std=c11

SRC=rdp.c
OUT=rdp

.PHONY: all run clean

all: $(OUT)

$(OUT): $(SRC)
	$(CC) $(CFLAGS) $(SRC) -o $(OUT)

run: all
	./$(OUT)

clean:
	rm -f $(OUT)
