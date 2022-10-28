/**
Names: Anthony Bruno and Jared Follet
Pledge: I pledge my honor that I have abided by the Stevens Honor System.
**/
#include<string.h>
#include<errno.h>
#include<stdio.h>
#include<stdlib.h>
#include<unistd.h>
#include<sys/wait.h>
#include <dirent.h>
#include <getopt.h>

int usage_check(char* str){
	char *usage = "Usage: ./spfind -d <directory> -p <permissions string> [-h]";
	
	for(int i = 0; i < 5; i++){
		if( str[i] != usage[i]){
			return 1;
		}
	}
	return 0;
}

int main(int argc, char* argv[]) {
	// pipe for sort-to-parent communication 
	int pipe_sort2parent[2];
	// pipe for parent-to-sort communication
	int pipe_pfind2sort[2]; 
	//pfind -> sort -> parent
	
	pipe(pipe_sort2parent);
	pipe(pipe_pfind2sort);
	
	if (pipe(pipe_pfind2sort) < 0){
		fprintf(stderr, "Error: Cannot create pipe_pfind2sort. %s.\n", strerror(errno));
		return EXIT_FAILURE;
	}
    if (pipe(pipe_sort2parent) < 0){
		fprintf(stderr,"Error: Cannot create pipe_sort2parent. %s.\n", strerror(errno));
		return EXIT_FAILURE;
    }
	
	pid_t pid[2]; 
	
	if ((pid[0] = fork()) == 0) { //if in CHILD
		// pfind     
		// Close all unrelated file descriptors.
		close(pipe_sort2parent[0]);
		close(pipe_sort2parent[1]);
		
	   if (dup2(pipe_pfind2sort[1], STDOUT_FILENO) < 0) {
		   //fprintf(stderr, "Error: Failed to dup2 1.\n");
		   close(pipe_pfind2sort[1]);
		   close(pipe_pfind2sort[0]);
		   return EXIT_FAILURE;        
	   }
	    execv("pfind",argv);
	    
		close(pipe_pfind2sort[0]);
		close(pipe_pfind2sort[1]);
		printf("Error: pfind failed.\n");
	} else if ((pid[1] = fork()) == 0) { 
		// sort     
		// Close all unrelated file descriptors.
		close(pipe_pfind2sort[1]);
		close(pipe_sort2parent[0]);
		
		if(dup2(pipe_pfind2sort[0],STDIN_FILENO) < 0) {
			//fprintf(stderr, "Error: Failed to dup2 2.\n");
			close(pipe_sort2parent[0]);
			close(pipe_sort2parent[1]);
			return EXIT_FAILURE;
		}
		
		if(dup2(pipe_sort2parent[1],STDOUT_FILENO) < 0) {
			//fprintf(stderr, "Error: Failed to dup2 3.\n");
			close(pipe_sort2parent[1]);
			close(pipe_sort2parent[0]);
			return EXIT_FAILURE;
		}
		
		execlp("sort", "sort",NULL);

		close(pipe_sort2parent[1]);
		close(pipe_pfind2sort[0]);
		printf("Error: pfind failed.\n");
	}
	 close(pipe_sort2parent[1]);
	 
	 if(dup2(pipe_sort2parent[0], STDIN_FILENO) < 0) {
		//fprintf(stderr, "Error: Failed to dup2 4.\n");
		close(pipe_sort2parent[0]);
		close(pipe_sort2parent[1]);
		return EXIT_FAILURE;
		}
	 
	 // Close all unrelated file descriptors. 
	 close(pipe_pfind2sort[0]);    
	 close(pipe_pfind2sort[1]);    
	
	
	 char buffer[8192];
	 
	 size_t count = read(STDIN_FILENO, buffer, sizeof(buffer) - 1);    
	 if (count == -1) {
		 perror("read()");
		 exit(EXIT_FAILURE);    
	 }   
	
	 
	 buffer[count] = '\0';
	 int matches = 0;
	 for(int i = 0; i < count; i++){
		 if(*(buffer + i) == '\n'){
			 matches++;
		 }
	 }
	 printf("%s",buffer);
	 
	 int status;
	 //int status2;
	waitpid(pid[0],&status, WUNTRACED);
	 //waitpid(pid[1],&status2, WNOHANG);
	wait(NULL);

	if((WEXITSTATUS(status) == 0) && usage_check(buffer)){
		printf("Total matches: %d\n", matches);
	}
	return EXIT_SUCCESS;
	
}
