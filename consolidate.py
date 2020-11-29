hiInFile = open("../hi-en/parallel/IITB-SR-aug.en-hi.100k.hi", 'r')
enInFile = open("../hi-en/parallel/IITB-SR-aug.en-hi.100k.en", 'r')

hiOutFile = open("../hi-en/parallel/IITB-SR-aug-con.en-hi.100k.hi",'w')
enOutFile = open("../hi-en/parallel/IITB-SR-aug-con.en-hi.100k.en",'w')


def nextLine(inputFile):
	line = inputFile.readline()
	if line == '':
		return -1, ''
	line = line.split("::")
	idx = int(line[0])
	line = line[1]
	return idx, line


hiIdx, hiLine = nextLine(hiInFile)
enIdx, enLine = nextLine(enInFile)

linecounter = 0

while(True):
	if linecounter == 10000:
		hiOutFile.flush()
		enOutFile.flush()
		linecounter = 0
	linecounter = linecounter + 1
	if hiIdx== -1 or enIdx == -1:
		break
	if hiIdx == enIdx:
		hiOutFile.write(hiLine)
		enOutFile.write(enLine)
		hiIdx, hiLine = nextLine(hiInFile)
		enIdx, enLine = nextLine(enInFile)
	elif hiIdx < enIdx:
		enIdx, enLine = nextLine(enInFile)
	else:
		hiIdx, hiLine = nextLine(hiInFile)
	

hiOutFile.close()
enOutFile.close()
hiInFile.close()
enInFile.close()