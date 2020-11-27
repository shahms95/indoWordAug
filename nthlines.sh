# first argument is filename; ct is nth line
ct=16
awk -v patt="$ct" 'NR % patt' $1