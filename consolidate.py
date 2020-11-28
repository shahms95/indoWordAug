hiFile = "../hi-en/parallel/IITB-SR-aug.en-hi.100k.hi"
enFile = "../hi-en/parallel/IITB-SR-aug.en-hi.100k.en"

hiOutFile = open("../hi-en/parallel/IITB-SR-aug-con.en-hi.100k.hi",'w')
enOutFile = open("../hi-en/parallel/IITB-SR-aug-con.en-hi.100k.en",'w')

with open(hiFile,'r') as f:
	hiLines = f.readlines()
with open(enFile,'r') as f:
	enLines = f.readlines()

hiIndex, enIndex = 0, 0

while(hiIndex < len(hiLines) and enIndex < len(enLines)):
	hiLine, enLine = hiLines[hiIndex].split("::"), enLines[enIndex].split("::")
	hiln = int(hiLine[0])
	enln = int(enLine[0])
	if( hiln == enln ):
		hiOutFile.write(hiLine[1])
		enOutFile.write(enLine[1])
		hiIndex = hiIndex + 1
		enIndex = enIndex + 1
	elif( hiln < enln):
		hiln = hiln + 1
	else:
		enln = enln + 1 

hiOutFile.close()
enOutFile.close()