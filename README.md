# SUSY_WEIGHTED_V1
# SUSY_WEIGHTED_V1: from the SUSY.csv test file (https://archive.ics.uci.edu/ml/datasets/SUSY), create a valued SUSY.csv that, together with a program included in the project, allows obtain success rates higher than 78%. This hit rate is low but is within the range of the hit rate of other published works cited in the references, with greater simplicity and availability of the code.
Resources: Java 8

Functioning:

Once the .jar and .bat files have been downloaded to a directory and assuming that SUSY.csv is in the c: directory, PrepareSusyWeighted_v1_0.bat is executed which creates a version of SUSY.csv valued with the name SusyWeighted78PercentHits.txt.

This file serves as input to the AssignClassWithSusyWeighted_v1.bat procedure, which also appears as a test file c \: SUSY.csv.

The procedure takes as parameters the margins of SUSY.csv that are considered a training file: from 0 to 4,500,000 according to the specifications on the SUSY.csv download page

When executing it, a rate of 3,465,879 hits is obtained, which of the 4,500,000 records selected from SUSY.csv represents a precision of 77.02%.

Once the evaluated SUSY has been built, AssignClassWithSusyWeighted_v1.bat can be executed, which uses the one generated in the previous step as a training file and the 500,000 last SUSY.csv records as a test file, also according to the specifications on the download page. from SUSY.csv.
Obtaining a hit rate of 390,494, 78.09%

In both procedures, the SUSY.csv margins to be considered as a test and training file are parameterized.

In AssignClassWithSusyWeighted.bat you can change C: \ SUSY.csv for any test file that has a structure analogous to SUSY obtaining the result with the classes assigned in the FileTextWithClassAsigned.txt file

### Cite this software as:
 ** Alfonso Blanco García ** SUSY_WEIGHTED_V1

More detailed description:

It begins with a statistical approach: each value of the first 8 significant fields (only these fields are considered as the rest are the result of complex calculations from the first 8 original fields) is associated with an index.

To associate this index to each value of each field, the most suitable width of the samples for each field has been previously estimated.

The frequencies of each field are established in each index that represents a sampling width.

In the preparation of Susy Weighted, the suggestion indicated in the textbook Artificial Intelligence A Modern Approach, Stuart Rosell, Peter Norvig (Third edition), page 749 and following, regarding the weighted training set, has been followed.

With one caveat, and that is that it has been impossible to achieve an accuracy of 83% in a first step from which, with the subsequent production of valued susy files, an accuracy of 90% could be reached, this was achieved in a previous project that was unsuccessful.
due to the excessive adaptation to the training file, but which then turned out to be detrimental when applied to a different test file.

It is pending to optimize in later versions

References:

https://archive.ics.uci.edu/ml/datasets/SUSY
Artificial Intelligence A Modern Approach, Stuart Rosell, Peter Norvig (Third edition), page 749 and following referring to weighted training sets.

Regarding precision:

1. The results offered on the website: http://physics.bu.edu/~pankajm/ML-Notebooks/HTML/NB5_CVII-logreg_SUSY.html

2. "Using Pyspark Environment for Solving a Big Data Problem: Searching for Supersymmetric Particles"
Mourad Azhari, Abdallah Abarda, Badia Ettaki, Jamal Zerouaoui, Mohamed Dakkon
International Journal of Innovative Technology and Exploring Engineering (IJITEE)
ISSN: 2278-3075, Volume-9 Issue-7, May 2020
https://www.researchgate.net/publication/341301008_Using_Pyspark_Environment_for_Solving_a_Big_Data_Problem_Searching_for_Supersymmetric_Particles
