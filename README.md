# Autocomplete Provider for a Mobile Device  
Asymmetrik Code Challenge
Steven Shearing

## General Information

This repository contains code for an Autocomplete Provider for the mobile
device keyboard programming challenge. There are four classes provided: the
AutocompleteProvider class, the Candidate class, a TernaryTree class, and a
Driver class to run and test the Autocomplete Provider. The Driver handles all
the IO, and simply interfaces with the AutocompleteProvider based on user
input. 

## Build, Run, Test

** Build **  

To build, a Makefile has been provided. Simply run the following command:
make

To build individual files, run the following command:  
javac <filename>.java

** Run **

To run the program, run the following command:
java Driver

** Test **

To test the program, the Driver has been outfitted with 4 user choices:
train from stdin (t): train on a string provided through standard input.
train from file (f): train on the contents of a file.
get complettions (i): input a string fragment, get Autocomplete Candidates.
quit (q): end program.

At the choice selection stage of the program, simply enter which of the four
choices you wish to execute by entering the corresponding character code,
followed by a newline. You will then be further prompted to provide the
corresponding necessary information to execute that choice. For example, for
choice (t), the user is queried for a training string, while for choice (f)
the user is queried for a filename. Enter the information, followed by a
newline. After the algorithm runs, if the user has not signaled end of input
or quit the program, the user will be prompted again for a choice to execute.

## Design Choices

** Driver **

The Driver provided has four options: two for training, one for completions,
and one to quit. To remind users of the choices, a welcome menu is printed
when the program begins running. The two training options were provided for
ease of use. The standard input training allows quick use of the program,
while the training from file allows larger training inputs to be provided more
easily. Note that when training from a file, the Driver will load the entirety
of the file's contents into a single String at once. For extremely large texts
(more than a million words), this is not very effecient memory wise. To allow
for much larger training corpora, the Driver could be modified to load chunks
of the file one at a time, with a predetermined size, then individually train
on the chunks. However, since this program is designed for mobile, I do not
expect huge input sizes, so I did not worry about this in the provided Driver.

Another important note about this particular Driver is that it does not save
training across sessions. If a new session is started, it will not remember
any training from previous sessions. This is because the underlying model (the
TernaryTree) is never saved - it is only stored in memory. In order to be able
to save training across sessions, we would need to extend the TernaryTree
class to include save(String filename) and load(String filename) methods.
save(String filename) would write the TernaryTree to a file, and
load(String filename) would load the TernaryTree from a file. Within a
singular session however, we can train the model as many times as we like.

** AutocompleteProvider **

The AutocompleteProvider follows the interface provided by the challenge
exactly. It provides a way to train the completion algorithm, and a way to use
that algorithm. Note that in training, since it was unclear what kind of
training passages we might see, or how well formatted we could expect the text
to be, I made two design decisions. First, I expect the input to be in the
English language - as such, I filter out any non-latin character. We can
easily change this for other languages by simply changing the accepted
alphabet. Second, I further filter out punctuation, numbers, and any other
special symbols, except for apostraphes (') and hyphens (-). This is because
we can reasonably expect to see apostraphes and hyphens in English words, but
would not expect well formatted words to have other characters.

This filtering scheme is not shared for getting the completions of an input
word. I make no changes to the input fragment, except filtering out any
quotation marks. If the fragment is poorly formatted, the algorithm will
simply not find any completions for the fragment.

** TernaryTree **

The underlying data structure in the AutocompleteProvider is a TernaryTree.
Originally, I had planned to use a Trie as the underlying datastructure.
Trie's have O(n) lookup and O(n) insertion, where n is the length of the key.
Unfortunately, despite being very effecient time wise, they use a lot of
wasted space. Every Trie node has 26 children, quite a few of which may never
get used (we wouldn't expect to see some combination of letters ever, such as
"zzc"). To solve the memory issue, I instead used a TernaryTree. TernaryTrees
give up a little bit of time effeciency to gain a lot of space effeciency.
Each node in the TernaryTree has exactly 3 children - reducing our memory
overhead significantly.

One possible issue with TernaryTree's is that they can beccome degenerate. For
example, if strings are added to the tree in alphabetical order, the
TernaryTree would degenerate into linked lists, resulting in extremely bad
time complexity performance. To solve this, the AutocompleteProvier shuffles
the training words randomly before providing them to the TernaryTree, which
makes the chance of degeneracy almost vanish. Furthermore, I initialize the
root node to always have character key 'm', since 'm' is in the middle of the
latin alphabet, in the hopes of kickstarting balance. Note that it is possible
to create self-balancing ternary trees, but that increases the training
complexity and felt like it was out of the scope for this challenge: random
shuffling of the input words create roughly balanced trees in the average
case. 

