###############################################################################
# Author: Anthony Bruno
# Date: 2/7/2020
# Description: Simple bash script to provide the basic functionality of a recycle bin.
###############################################################################
#!/bin/bash

flag_flag=0
count_flag=0
help_flag=0
list_flag=0
purge_flag=0

readonly script_location=`realpath $0`
readonly dirname="/home/cs392/.junk"

usage(){
	cat << ENDOFTEXT
Usage: $(basename "$script_location") [-hlp] [list of files]    
   -h: Display help.    
   -l: List junked files.   
   -p: Purge all files.    
   [list of files] with no other arguments to junk those files.
ENDOFTEXT
}

if [ $# -ne 0 ]; then
	while getopts ":hlp" option; do
		case "$option" in
			h) help_flag=1
			   (( ++count_flag ))
			   ;;
			l) list_flag=1
			   (( ++count_flag ))
			   ;;
			p) purge_flag=1
			   (( ++count_flag ))
			   ;;
			?) printf "Error: Unknown option '-%s'.\n" $OPTARG >&2
			   flag_flag=1
			   break
			   ;;
		esac
	done
else #No flags passed.
	flag_flag=1
fi
#Flag/Arguement handling
#if (one or more flags AND arguement) OR if (no flags and 
if [[ $count_flag -gt 0 && $# -gt 1  ||  $count_flag -gt 1 ]]; then
	echo "Error: Too many options enabled."
	flag_flag=1
fi

if [ $flag_flag -eq  1 ]; then
	usage
	exit 1
fi

#Checking .junk directory
if [ ! -d $dirname ]; then
	mkdir $dirname
	chmod 777 $dirname
else
	chmod 777 $dirname
fi
#If help flag enabled.
if [ $help_flag -gt 0 ]; then
	usage
	exit 0
fi
#If list flag enabled.
if [ $list_flag -gt 0 ]; then
	cd $dirname
	ls -lAF
	cd - > /dev/null
	exit 0
fi

if [ $purge_flag -gt 0 ]; then
	rm -rf "$dirname"/{*,.*} 2> /dev/null
	exit 0
fi

for file in "$@"; do
	if [ -f $file ] || [ -d $file ]; then
		mv "$file" "$dirname"/
	else
		echo "Warning: '$file' not found."
	fi
done

exit 0