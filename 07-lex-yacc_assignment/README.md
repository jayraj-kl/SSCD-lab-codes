IF THEN ROP AOP NUM ID

IF x < y THEN z = 10

if → IF
then → THEN
< → ROP
= → AOP
digits like 123 → NUM
names like x, total_1 → ID

start: sif
sif: IF cmpn THEN stmt
cmpn: ID ROP ID
stmt: ID AOP NUM
