from nltk.corpus import wordnet as wn
from random import randint


debug = False
def printd(lines):
	if debug:
		print(lines)

inputFile = "../hi-en/parallel/IITB.en-hi.100k.en"
outputFileName = "../hi-en/parallel/IITB-SR-aug.en-hi.100k.en"
# inputFile = "../hi-en/dev_test/dev.en"
# outputFileName = "../hi-en/dev_test/dev-SR-aug.en"
outputFile = open(outputFileName, 'w')

with open(inputFile,'r') as f:
	lines = f.readlines()

counter = 5
linecount = 0
validPOS = []
# validPOS.append(wn.NOUN)
validPOS.append(wn.VERB)
validPOS.append(wn.ADV)
validPOS.append(wn.ADJ)

numWordsLimit = 4
for inputLine in lines:
	linecount = linecount+1
	# if (debug and counter<0):
	# 	break
	# counter = counter - 1
	words = inputLine.split()
	numWords = len(words)
	if numWords <numWordsLimit:
		continue

	outputFile.write(str(linecount) + "::" + inputLine)
	numReplacedWords = int(max(1,0.15*numWords))
	printd("Num replaced words: {}".format(numReplacedWords))
	replaceIndexes = []
	printd("Line number: {}".format(linecount))
	for x in range(numReplacedWords):
		replaceIndexes.append(randint(0,numWords-1))

	for replaceIndex in replaceIndexes:
		originalWord = words[replaceIndex]
		printd("Word: {} at index: {}".format(originalWord, replaceIndex))
		# synset = set()
		for pos in validPOS:		
			for s in wn.synsets(originalWord, pos = pos):
				synsetLimit = 3
				for l in s.lemmas():
					# synset.add(l.name())
					synsetWord = l.name()
					if(synsetLimit<1):
						break
					synsetLimit = synsetLimit - 1
					words[replaceIndex] = synsetWord
					changedLine = str(linecount) + "::" + " ".join(words) + '\n'
					printd("Changed line: {}".format(changedLine))
					outputFile.write(changedLine)

		words[replaceIndex] = originalWord
outputFile.close()