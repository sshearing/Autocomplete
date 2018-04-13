JFLAGS = Xlint:all
JC = javac

.SUFFIXES: .java .class
.java.class:
	$(JC) $(JFGLAGS) $*.java

CLASSES = Driver.java AutocompleteProvider.java TernaryTree.java Candidate.java

default: classes

classes: $(CLASSES:.java=.class)

checkstyle:
	checkstyle -c cs328_checks.xml *.java

clean:
	$(RM) *.class *~ 
