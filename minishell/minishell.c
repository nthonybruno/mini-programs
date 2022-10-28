
/*
    Name: Nicholas Szegheo and Anthony Bruno
    Assignment: Minishell in C
    Pledge: I pledge my honor that I have abided by the Stevens Honor System.
    Date: 4/15/2020
*/
#include <ctype.h>
#include <errno.h>
#include <limits.h>
#include <pwd.h>
#include <pthread.h>
#include <stdio.h>
#include <setjmp.h>
#include <stdlib.h>
#include <signal.h>
#include <string.h>
#include <stdbool.h>
#include <sys/wait.h>
#include <sys/types.h>
#include <unistd.h>

#define BUFSIZE 4096
#define MAX_ARGS 64
#define PATH_MAX 4096

#define BRIGHTBLUE "\x1b[34;1m"
#define DEFAULT    "\x1b[0m"

pid_t process;
sigjmp_buf jmpbuf;
sig_atomic_t flagger;

void catch_signal(int sig){
  int check;
    if (flagger == 0){
        printf("\n");
    }
    if((check = waitpid(process, NULL, WNOHANG)) == -1){
        write(STDOUT_FILENO, NULL, 1);
  }
    flagger =1;
    siglongjmp(jmpbuf, 1);
}

int cd(char *path){
    if (strcmp(path, "~") == 0){
        //Home directory case.
        struct passwd *pw;
        if ( (pw = getpwuid(getuid())) == NULL ){
            fprintf(stderr, "Error: Cannot get passwd entry. %s.\n", strerror(errno));
            return 1;
        }
        if (chdir(pw->pw_dir) == -1){
            fprintf(stderr, "Error: Cannot change directory to '%s'. %s.\n", path ,strerror(errno));
            return 1;
        }
    }else{
        if (chdir(path) == -1){
            fprintf(stderr, "Error: Cannot change directory to '%s'. %s.\n", path ,strerror(errno));
            return 1;
        }
    }
    return 0;
}

void  parse(char *buf, char **args){
  //clean the input of all spaces
  //buf: input char array
  //args:
  while (*buf != '\0') {
        while (*buf == ' ' || *buf == '\t' || *buf == '\n'){
      //incremement pointer and set equal to null terminator.
            *buf++ = '\0';
        }
        *args++ = buf;
    //While there is no terminator or space, incrememnt buf.
        while(*buf != '\0' && *buf != ' ' && *buf != '\n' && *buf != '\t'){
            buf++;
        }
    } //null terminator args
    *args = '\0';
}

int execute(char **args){
    pid_t pid;
    int status;

    if ((pid = fork()) < 0) { // Fork from parent
            fprintf(stderr, "Error: fork() failed. %s.\n", strerror(errno));
            return 1;
    }
    else if (pid == 0) { // In child fork.
        if (execvp(*args, args) < 0) {
            fprintf(stderr, "Error: exec() failed. %s.\n", strerror(errno));
            return 1;
        }
        flagger = 0;
    }
    else { // Wait for completion in parent

        process = pid;
        while (wait(&status) != pid);
    }
    return 0;
}

int main()
{
    struct sigaction action;
    memset(&action, 0, sizeof(struct sigaction));
    action.sa_handler = catch_signal;
    action.sa_flags = SA_RESTART;
    if (sigaction(SIGINT, &action, NULL)==-1){
        fprintf(stderr, "Error: Cannot register signal handler. %s\n", strerror(errno));
        return EXIT_FAILURE;
    }
    sigsetjmp(jmpbuf, 1);

    char buf[BUFSIZE];
    char *args[MAX_ARGS + 1];
    char cwd[PATH_MAX];

    // Main Loop
    while (true){
        if (getcwd(cwd, sizeof(cwd)) != NULL){
            printf("[%s%s%s]$ ", BRIGHTBLUE, cwd, DEFAULT);
        }else{
          fprintf(stderr, "Error: Cannot get current working directory. %s.\n", strerror(errno));
          // If we can't get the cwd, we can't take input.
          // This would loop forever if we continued.
          return EXIT_FAILURE;
        }
        fflush(stdout);

        // Copied input handler from prompt.c
        ssize_t  bytes_read = read(STDIN_FILENO, buf, BUFSIZE -1);

        if(bytes_read > 0){
            buf[bytes_read - 1] = '\0';
        }
        if (bytes_read == 1){
            continue;
        } // Exit checking
        if (strncmp(buf, "exit", 4) ==0){
            break;
        }
        // Parse input
        parse(buf, args);
        // Check for cd: If it is not cd, fork and exec
        if (strcmp(*args,"cd") == 0){
            if (args[2] != NULL){ //Too many args!
                fprintf(stderr, "Error: Too many arguments to cd.\n");
                continue;
            }
            if (args[1] == NULL){
                if (cd("~") != 0){
                    continue;
                }
            }else{
                if (cd(args[1]) != 0){
                    continue;
                }
            }
        }else{
            if (execute(args) != 0){
                continue;
            }
        }
    }
    return EXIT_SUCCESS;
}
