#include <arpa/inet.h>
#include <errno.h>
#include <fcntl.h>
#include <stdio.h>
#include <stdlib.h>
#include <string.h>
#include <sys/socket.h>
#include <unistd.h>
#include "util.h"

int client_socket = -1;
char username[MAX_NAME_LEN + 1];
char inbuf[BUFLEN + 1];
char outbuf[MAX_MSG_LEN + 1];

int handle_stdin() {
	if(get_string(outbuf, MAX_MSG_LEN) == TOO_LONG) {
		fprintf(stderr, "Sorry, limit your message to %d characters.\n", MAX_MSG_LEN);
		return EXIT_FAILURE;
	}
	else {
		ssize_t bytes_sent = send(client_socket, outbuf, strlen(outbuf), 0);
		
		if(bytes_sent == -1) {
			fprintf(stderr, "Error: %s\n", strerror(errno));
			//return EXIT_FAILURE;
		}
	}

	int iseq = strcmp(outbuf, "bye");
	//check if the message is equal to bye, which is the exit message.
	if ( iseq == 0) {
		printf("Goodbye.\n");
		close(client_socket);
		exit(1);
		return EXIT_SUCCESS;
	}

	return EXIT_SUCCESS;
} 

int handle_client_socket() {
	ssize_t bytes_recieved = recv(client_socket, inbuf, BUFLEN, 0); // no flags
	
	if (bytes_recieved == -1) {
		fprintf(stderr, "Warning: Failed to receive incoming message. %s.", strerror(errno));
		//warn the user.
	}
	else if (bytes_recieved == 0) {
		//the server abruptly broke the connection with the client, as in the server crashed or perhaps the network failed. 
		fprintf(stderr, "\nConnection to server has been lost.\n");
		close(client_socket);
		exit(-1);		//not sure if needed.
		return EXIT_FAILURE;
	}
	else {
		ssize_t eos = bytes_recieved;
		inbuf[eos] = '\0';
		
		int iseq2 = strcmp(inbuf, "bye");
		
		if ( iseq2 == 0) {
			printf("\nServer initiated shutdown.\n");
			exit(-1);
			return EXIT_FAILURE;
		}
		
		printf("\n%s\n", inbuf);
	}

	return EXIT_SUCCESS;
}



int main(int argc, char *argv[]) {
	//Check that there are 3 arguements, regardless of correctness.
	if (argc != 3) {
		printf("Usage: %s <server IP> <port>\n", argv[0]); //prints the message Usage: ./chatclient <server IP> <port>
		return EXIT_FAILURE;
	}
	
//Valid IP logic below
	//  int inet_pton(int af, const char *src, void *dst);
	// ipv4 address so af = AF_INET
	// src = character string to be converted into ip address.
	// copies the network address structure to dst.
	// dst is written in network byte order.
	char *ipstring = argv[1];
	
	/*
	struct sockaddr_in {
               sa_family_t    sin_family; // address family: AF_INET
               in_port_t      sin_port;   // port in network byte order
               struct in_addr sin_addr;   // internet address 
           };
	*/
	struct sockaddr_in server_addr;    
	socklen_t addrlen = sizeof(struct sockaddr_in);
	int temp;
	int temp2;
	memset(&server_addr, 0, addrlen); // Zero out structure
	server_addr.sin_family = AF_INET;   // Internet address family
	
	if (inet_pton(AF_INET, ipstring, &server_addr.sin_addr) != 1) {
        fprintf(stderr, "Error: Invalid IP address '%s'.\n", ipstring);
        return EXIT_FAILURE;
    }
//Valid IP logic above

//Valid Port logic below
	//The port must be an integer in the range [1024, 65535]
    int port = 0;
	char *portstring = argv[2];
	
	// bool parse_int(const char *input, int *i, const char *usage);
	// parse_int returns a bool, it attempts to convert a string into an int and stores it in i.
    if (parse_int(portstring, &port,"port number")) { //if successful
    	if (port < 1024 || port > 65535) { //check if converted port num is not in range.
    		fprintf(stderr, "Error: Port must be in range [1024, 65535].\n");
    		return EXIT_FAILURE;
    	}
		else {
		server_addr.sin_port = htons(port); //set the struct port address to port.
		}
    }
    else {
    	return EXIT_FAILURE;
    }
  
//Prompting for a username
	int max_name_len_plus_null = MAX_NAME_LEN + 1; // 21
    while (!(strlen(username) > 0)) {
		//Dont have to worry about username being only spaces.
    	printf("Enter your username: ");
		fflush(stdout);
		
		int length = get_string(username, max_name_len_plus_null); 
		//check if username length is 0 or too long.
    	if (length == NO_INPUT) {
    		continue;
    	}
    	else if (length == TOO_LONG) {
    		fprintf(stderr, "Sorry, limit your username to %d characters.\n", MAX_NAME_LEN);
			memset(&username, 0, max_name_len_plus_null);
    		continue;
    	}
    }
	
	int end_pos = strlen(username);
    username[end_pos] = '\0'; //null terminate 


    printf("Hello, %s. Let's try to connect to the server.\n", username);
//end of username logic.

//establishing connection

		// socket(int socket_family, int socket_type, int protocol);
		
    if ((client_socket = socket(AF_INET, SOCK_STREAM, 0)) < 0) { // Create a reliable, stream socket using TCP.    
        fprintf(stderr, "Error: Failed to create socket. %s.\n",strerror(errno));
        return EXIT_FAILURE;
    } //client.c

	// Establish the connection to the server. client.c
    if (connect(client_socket, (struct sockaddr *)&server_addr, addrlen) < 0) {
		fprintf(stderr, "Error: Failed to connect to server. %s.\n", strerror(errno));
		return EXIT_FAILURE;
	}

	// try to recieve welcome message. 
	int bytes_recvd = recv(client_socket, inbuf, BUFLEN - 1, 0);
	//  ssize_t recv(int sockfd, void *buf, size_t len, int flags);
	// no flags needed, length will be up to buffer minus 1 in order to null termiante.
	// we are storing the recieved bytes in inbuf.
	
	
	if (bytes_recvd > 0) {
		inbuf[bytes_recvd] = '\0'; //null terminate inbuf which contains all the messages.
	}
	else if(bytes_recvd < 0){
		fprintf(stderr, "Error: Failed to receive message from server. %s.\n", strerror(errno));
		return EXIT_FAILURE;
	}
	else { //if it equals 0
		//if the number of bytes received is 0, that means the server closed the connection on the client. 
		fprintf(stderr, "All connections are busy. Try again later.\n");
		return EXIT_FAILURE;
	}
	printf("\n%s\n", inbuf); //print a newline, the message, and 2 more newlines.
	printf("\n");
		
	// ssize_t send(int sockfd, const void *buf, size_t len, int flags);
	// sending the username stored in username. no flags necessary.
	if (send(client_socket, username, strlen(username), 0) < 0) {
		fprintf(stderr, "Error: Failed to send username to server. %s.\n", strerror(errno));
		return EXIT_FAILURE;
	}

	// void FD_SET(int fd, fd_set *set);
		// This macro adds the file descriptor fd to set.  
		// Adding a file descriptor that is already present in the set is a no-op, and does not produce an error.
		// takes in a file descriptor and fd_set type.
	fd_set output_descriptor; //declare

	FD_ZERO(&output_descriptor); // zero out/initiliaze
	//  FD_ZERO()
		// 	This macro clears (removes all file descriptors from) set.  It
		// 	should be employed as the first step in initializing a file
		// descriptor set.
	FD_SET(STDIN_FILENO, &output_descriptor); // add STDIN_FILENO to output_descriptor
	
	while (true) {	//initilaize a loop forever.	
		printf("[%s]: ", username); //print username.
		fflush(stdout);
		
		FD_ZERO(&output_descriptor); 
		FD_SET(STDIN_FILENO, &output_descriptor);
		FD_SET(client_socket, &output_descriptor);
		
		// int select(int nfds, fd_set *readfds, fd_set *writefds, fd_set *exceptfds, struct timeval *timeout);
		temp = select(client_socket + 1, &output_descriptor, NULL, NULL, NULL);
		
		if (temp == -1) { //on success, select returns # of fds. On error, 
			return EXIT_FAILURE;
		}

		if (FD_ISSET(STDIN_FILENO, &output_descriptor)) { // test if a file descriptor is still present in a set.
			
			temp2 = handle_stdin();
			if ( temp2 == -1) { //if handle returns in error 
				break;
			}
		}
		else if (FD_ISSET(client_socket, &output_descriptor)) { 
			temp2 = handle_client_socket(); 
			if (temp2 == -1) { //if handle_client_socket returns in error 
				break;
			}
		}
	}
	close(client_socket);
    return EXIT_SUCCESS;
} 