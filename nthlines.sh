# first argument is filename; ct is nth line
ct=2
awk -v patt="$2" 'NR % patt == 0' $1
