# Sorted Permission Finder in C
Utilizes the standard permision finder found at the same level in the parent directory. It uses fork() and exec() to create a child process
of the linux sort() program and the pfind executable.

    Usage: ./spfind -d <directory> -p <permissions string> [-h]

Example:

    ./spfind -d ~ -p rwxr-xr-x 2>/dev/null
