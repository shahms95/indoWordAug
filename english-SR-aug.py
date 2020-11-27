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
for inputLine in lines:
	if debug && counter<0:
		break
	counter = counter - 1
	outputFile.write(str(linecount) + "::" + inputLine)
	words = inputLine.split()
	numWords = len(words)
	numReplacedWords = int(max(1,0.15*numWords))
	printd("Num replaced words: {}".format(numReplacedWords))
	replaceIndexes = []
	for x in range(numReplacedWords):
		replaceIndexes.append(randint(0,numWords-1))

	for replaceIndex in replaceIndexes:
		originalWord = words[replaceIndex]
		printd("Word: {} at index: {}".format(originalWord, replaceIndex))
		synset = set()
		for s in wn.synsets(originalWord):
			for l in s.lemmas():
				synset.add(l.name())

		printd("Size of synset: {}".format(len(synset)))
		synsetLimit = 3
		for synsetWord in synset:
			if(synsetLimit<0):
				break
			synsetLimit = synsetLimit - 1
			words[replaceIndex] = synsetWord
			changedLine = str(linecount) + "::" + " ".join(words) + '\n'
			printd("Changed line: {}".format(changedLine))
			outputFile.write(changedLine)

		words[replaceIndex] = originalWord
	linecount = linecount+1
outputFile.close()