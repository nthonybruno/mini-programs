#include <ctype.h>
#include <errno.h>
#include <getopt.h>
#include <unistd.h>
#include <limits.h>
#include <math.h>
#include <pthread.h>
#include <stdbool.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/sysinfo.h>
#include <time.h>

typedef struct arg_struct {
    int start;
    int end;
} thread_args;

pthread_mutex_t lock;
int total_count = 0;

void *sieve(void *ptr){
    thread_args *targs = (thread_args *)ptr;
    int s_val = targs->start, e_val = targs->end;
    int maxim = e_val - s_val + 1;
    bool *lprimes = (bool *)malloc(sizeof(bool) * e_val);
    bool *hprimes = (bool *)malloc((maxim) * sizeof(bool));
    int count = 0;
    int retval;

    for(int i = 2; i < e_val; i++){
        lprimes[i] = 1;
    }

    for (int i = 2; i < sqrt(e_val); i++){
        if (lprimes[i]){
            for (int j = i * i; j < e_val; j += i){
                lprimes[j] = 0;
            }
        }
    }

	for (int i = 0; i < maxim; i++) {
		hprimes[i] = 1;
	}

	for (int i = 2; i < e_val; i++) {
		if (lprimes[i]) {
			int j = ceil((double)(s_val)/i) * i - s_val;
			if (s_val <= i) {
				j = j + i;
			}
            while(j<maxim) {
				hprimes[j] = 0;
                j += i;
			}
		}
	}

	free(lprimes);

    for (int i = 0; i < maxim; i++){
        if(hprimes[i]){
            int counted_nums = 0;
            int temp = i + s_val;
            while(temp > 0 && counted_nums < 2){
                if (temp % 10 == 3){
                    counted_nums++;
                }
                temp = temp / 10;
            }
            if (counted_nums > 1){
               count++;
            }
        }
    }

	if ((retval = pthread_mutex_lock(&lock)) != 0) {
		 fprintf(stderr, "Warning: Cannot lock mutex. %s.\n",strerror(retval));
	}

	total_count += count;
	if ((retval = pthread_mutex_unlock(&lock)) != 0) {
			 fprintf(stderr, "Warning: Cannot unlock mutex. %s.\n",strerror(retval));
    }
    free(hprimes);
    pthread_exit(NULL);
}


int main(int argc, char *argv[]){
    if (argc == 1){
        fprintf(stderr, "Usage: ./mtsieve -s <starting value> -e <ending value> -t <num threads>\n");
        return EXIT_FAILURE;
    }

    int op;
    int s_flag = 0, e_flag = 0, t_flag = 0;
    int s_val = 0, e_val = 0, t_val = 0;
    char *ptr1;
    char *ptr2;
    char *ptr3;

    opterr = 0;
    while ((op = getopt(argc,argv, "s:e:t:")) != -1){
          switch(op) {
              case 's':
                    s_flag = 1;
                    for(int i = 0; i < strlen(optarg); i++){
                        if(!isdigit(optarg[i])){
                            fprintf(stderr, "Error: Invalid input '%s' received for parameter '-%c'.\n", optarg, op);
                            return EXIT_FAILURE;
                        }
                    }
                    if(!(strtol(optarg, &ptr1, 10) <= INT_MAX)){
                        fprintf(stderr,"Error: Integer overflow for parameter '-%c'.\n",op);
                        return EXIT_FAILURE;
                    } else {
                        s_val = atoi(optarg);
                    }
                    opterr=0;
                    break;
              case 'e':
                    e_flag = 1;
                    for(int i = 0; i < strlen(optarg); i++){
                        if(!isdigit(optarg[i])){
                            fprintf(stderr, "Error: Invalid input '%s' received for parameter '-%c'.\n", optarg, op);
                            return EXIT_FAILURE;
                        }
                    }
                    if(!(strtol(optarg, &ptr2, 10) <= INT_MAX)){
                        fprintf(stderr,"Error: Integer overflow for parameter '-%c'.\n",op);
                        return EXIT_FAILURE;
                    } else {
                        e_val = atoi(optarg);
                    }
                    break;
              case 't':
                    t_flag = 1;
                    for(int i = 0; i < strlen(optarg); i++){
                        if(!isdigit(optarg[i])){
                            fprintf(stderr, "Error: Invalid input '%s' received for parameter '-%c'.\n", optarg, op);
                            return EXIT_FAILURE;
                        }
                    }
                    if(!(strtol(optarg, &ptr3, 10) <= INT_MAX)){
                        fprintf(stderr,"Error: Integer overflow for parameter '-%c'.\n",op);
                        return EXIT_FAILURE;
                    } else {
                        t_val = atoi(optarg);
                    }
                    break;
              case '?':
                    if (optopt == 'e' || optopt == 's' || optopt == 't') {
                        fprintf(stderr, "Error: Option -%c requires an argument.\n", optopt);
                    } else if (isprint(optopt)) {
                        fprintf(stderr, "Error: Unknown option '-%c'.\n", optopt);
                    } else {
                        fprintf(stderr, "Error: Unknown option character '\\x%x'.\n", optopt);
                    }
                    return EXIT_FAILURE;
            }
    }

    //Additional Input validation

    if (argv[optind] != NULL){
        fprintf(stderr, "Error: Non-option argument '%s' supplied.\n", argv[optind] );
        return EXIT_FAILURE;
    }

    if (!s_flag){
        fprintf(stderr, "Error: Required argument <starting value> is missing.\n");
        return EXIT_FAILURE;
    }
    if (!(s_val >= 2)){
        fprintf(stderr, "Error: Starting value must be >= 2.\n");
        return EXIT_FAILURE;
    }
    if (!e_flag){
        fprintf(stderr, "Error: Required argument <ending value> is missing.\n" );
        return EXIT_FAILURE;
    }
    if (!(e_val >= 2)){
        fprintf(stderr, "Error: Ending value must be >= 2.\n");
        return EXIT_FAILURE;
    }
    if (s_val > e_val){
        fprintf(stderr, "Error: Ending value must be >= starting value.\n" );
        return EXIT_FAILURE;
    }
    if (!t_flag){
        fprintf(stderr, "Error: Required argument <num threads> is missing.\n" );
        return EXIT_FAILURE;
    }
    if (t_val < 1){
        fprintf(stderr, "Error: Number of threads cannot be less than 1.\n" );
        return EXIT_FAILURE;
    }
    if (t_val > 2*get_nprocs()){
        fprintf(stderr, "Error: Number of threads cannot exceed twice the number of processors(%d).\n", get_nprocs() );
        return EXIT_FAILURE;
    }

    //If number of values is less than number of threads, set threads.
    if ( (e_val - s_val) + 1 < t_val ){
        t_val = (e_val - s_val) + 1;
    }

    //Execute threading code

    printf("Finding all prime numbers between %d and %d.\n", s_val, e_val);

    int thread_length = (e_val - s_val + 1) / t_val;
    int thread_length_remainder = (e_val - s_val + 1) % t_val;

    int temp_start = s_val;
    int temp_end = s_val + thread_length - 1;

    int retval;
    if ((retval = pthread_mutex_init(&lock, NULL)) != 0) {
        fprintf(stderr, "Error: Cannot create mutex. %s.\n", strerror(retval));
        return EXIT_FAILURE;
    }

    pthread_t threads[t_val];
    thread_args targs[t_val];

    printf("%d segments:\n", t_val);

    for(int i = 0; i < t_val; i++){
        if (thread_length_remainder > 0){
            targs[i].start= temp_start;
            targs[i].end = temp_end + 1;
            thread_length_remainder -= 1;
        } else {
            targs[i].start= temp_start;
            targs[i].end = temp_end;
        }

        printf("   [%d, %d]\n", targs[i].start, targs[i].end);

        temp_start = targs[i].end + 1;
        temp_end = temp_start + thread_length - 1;

        if( (pthread_create(&threads[i], NULL, sieve, (void *)(&targs[i]))) != 0){
            fprintf(stderr, "Error: Cannot create thread %d. %s.\n", i + 1, strerror(errno));
            return EXIT_FAILURE;
        }

    }

    for (int i = 0; i < t_val; i++) {
        if (pthread_join(threads[i], NULL) != 0) {
            fprintf(stderr, "Warning: Thread %d did not join properly.\n", i + 1);
        }
    }

    if ((retval = pthread_mutex_destroy(&lock)) != 0) {
        fprintf(stderr, "Warning: Cannot destroy mutex. %s.\n", strerror(retval));
    }

    //Print results and exit.


    printf("Total primes between %d and %d with two or more '3' digits: %d.\n",s_val, e_val, total_count);

    return EXIT_SUCCESS;
}
