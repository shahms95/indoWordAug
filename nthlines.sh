# first argument is filename; second argument is nth line
awk -v patt="$2" 'NR % patt == 0' $1