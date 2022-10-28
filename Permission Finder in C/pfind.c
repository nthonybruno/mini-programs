/**
* Description: Recursively search for files whose permissions match 
*			   the permissions string starting in the specified directory.
**/

#include <dirent.h>
#include <errno.h>
#include <getopt.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/stat.h>
#include <unistd.h>


char *usage = "Usage: ./pfind -d <directory> -p <permissions string> [-h]";

int step3(char *dname,int permnum){
	
	DIR *dir;
	
	char path[PATH_MAX];
	
	struct dirent *entry;
	
    struct stat sb;
    
	char file_path[PATH_MAX];
    
	size_t pathlength = 0;
	
    if (realpath(dname, path) == NULL) {	//Copies entire path into "path"
        fprintf(stderr, "Error: Cannot get full path of file '%s'. %s\n", dname, strerror(errno));
        return EXIT_FAILURE;
    }

    if ((dir = opendir(path)) == NULL) { //Opens directory into dir
        fprintf(stderr, "Error: Cannot open directory '%s'. %s\n", path, strerror(errno));
        return EXIT_FAILURE;
    }

    file_path[0] = '\0';
    if (strcmp(path, "/")) {
		
        strncpy(file_path, path, PATH_MAX);
    }
	
    pathlength = strlen(file_path) + 1;
	
    file_path[pathlength - 1] = '/';
    
	file_path[pathlength] = '\0';

    while ((entry = readdir(dir)) != NULL) {
        if (strcmp(entry->d_name, ".") == 0 || strcmp(entry->d_name, "..") == 0) {
            continue;
        }
		
        strncpy(file_path + pathlength, entry->d_name, PATH_MAX - pathlength);
		
        if (lstat(file_path, &sb) < 0) {
            fprintf(stderr, "Error: Cannot stat file '%s'. %s\n", file_path, strerror(errno));
            continue;
        }
        if (entry->d_type == DT_DIR) { //If directory.
			
			if((sb.st_mode - (sb.st_mode & S_IFMT)) == permnum){ //compare permission strings
				printf("%s\n", file_path);
			}
			step3(file_path,permnum);
			
        } else { //if file
			
			if((sb.st_mode - (sb.st_mode & S_IFMT)) == permnum){ //compare permission strings
				printf("%s\n", file_path);
			}
        }
    }

    closedir(dir);
    return EXIT_SUCCESS;
}

int main(int argc, char **argv){
	int op;
	int d_flag = 0, p_flag = 0;
	char *d_arg, *p_arg;
	
	
	
	while ((op = getopt(argc,argv, ":d:p:h")) != -1){
		switch(op) {
			case 'd':
				d_flag = 1;
				d_arg = optarg;
				break;
			case 'p':
				p_flag = 1;
				p_arg = optarg;
				break;
			case 'h':
				puts(usage);
				return EXIT_SUCCESS;
			case '?':
				fprintf(stderr, "Error: Unknown option '%s' recieved.\n",argv[1]);
				return EXIT_FAILURE;
		}
	}
	
	if(!d_flag) {
		printf("Error: Required argument -d <directory> not found.\n");
		return EXIT_FAILURE;
	}
	
	if(!p_flag) {
		printf("Error: Required argument -p <permissions string> not found.\n");
		return EXIT_FAILURE;
	}
	
	//ensure correct arguement count
	
	
	if(argc == 5){
		
	}
	else {
		
	}
	
	
	/**
		TODO: if() //If d and h flag supplied but no arguements.
	{
		puts(usage);
		return EXIT_FAILURE;
	}
	**/
	
	
//			1 	  2      3       4         5             6
//Usage: ./pfind -d <directory> -p <permissions string> [-h]

//Need logic for determining position of file_pos,
	//can -p come before -d?
	
/**********************************************/
//Declaration of filepointer, file name string and permissions string.
	FILE *fp = fopen(d_arg, "r");
	
/**********************************************/
//Check if file exists.
	if(fp == NULL){
		fprintf(stderr,"Error: Cannot stat '%s'. No such file or directory.\n",d_arg);
		return EXIT_FAILURE;
	}
	
/**********************************************/
//Check if permission string is in valid format.
	if(strlen(p_arg) != 9){
		fprintf(stderr,"Error: Permissions string '%s' is invalid.\n",p_arg);
		return EXIT_FAILURE;
	}
	else{
		for(int i = 0; i < 9; i+=3){
			// r or -
			//pos 1 + x
			if(p_arg[i] != 'r' && p_arg[i] != '-')
			{
				fprintf(stderr,"Error: Permissions string '%s' is invalid.\n",p_arg);
				return EXIT_FAILURE;
			}
			// w or -
			//pos 2 + x
			if(p_arg[i+1] != 'w' && p_arg[i+1] != '-')
			{
				fprintf(stderr,"Error: Permissions string '%s' is invalid.\n",p_arg);
				return EXIT_FAILURE;
			}
			// x or -
			//pos 3 + x
			if(p_arg[i+2] != 'x' && p_arg[i+2] != '-')
			{
				fprintf(stderr,"Error: Permissions string '%s' is invalid.\n",p_arg);
				return EXIT_FAILURE;
			}
		}
	}
	/** Grab input permission string int value. **/
	int perm_sum = 0;
	
	int temp = 1;
	
	for(int i = 8; i >= 0; i--){
		if(p_arg[i] != '-'){
			perm_sum += temp;
		}
		temp = temp << 1;
	}
	
	step3(d_arg,perm_sum); 
	
	fclose(fp);
	return EXIT_SUCCESS;
}


