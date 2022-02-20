# SUSY_WEIGHTED_V1
# SUSY_WEIGHTED_V1: from the SUSY.csv test file (https://archive.ics.uci.edu/ml/datasets/SUSY), create a valued SUSY.csv that, together with a program included in the project, allows obtain success rates higher than 78%.

The main difference in this model comparing with others is that, in the statistical process, each field is sampled differently according to its contribution to the hit rate.


The hit rate is within the range of the hit rate of other published works cited in the references, with greater simplicity and availability of the code.

Resources: Java 8

On drive c: there should be the file SUSY.csv downloaded from https://archive.ics.uci.edu/ml/datasets/SUSY

The programs need to record files on disk, which allows their use on computers with little memory. For this reason, it is necessary to create a folder in the C: directory with the name of SUSY_WEIGHTED_V1 and to which writing rights are assigned to the user who executes the program. Ultimately c: drives are write-protected.
You can also change the directory and folder, but in that case you would have to change the references to C:\SUSY_WEIGHTED_V1 in the .bat files

Functioning:

Once the .jar and .bat files have been downloaded to a directory and assuming that SUSY.csv is in the C: directory:
Execute PrepareSusyWeighted_NaiveBayes_v1_0.bat
Which creates  the file C:\SUSY_WEIGHTED_V1\SusyWeighted78PercentHits.txt, wich is a version of SUSY.csv valued.

When executing it, a rate of 3,465,879 hits is obtained, which of the 4,500,000 records selected from SUSY.csv represents a precision of 77.02%, taht will be improve in the next step.

Once the evaluated SUSY has been built, AssignClassWithSusyWeighted_v1.bat can be executed, which uses the one generated in the previous step as a training file and the 500,000 last SUSY.csv records as a test file, also according to the specifications on the download page. from SUSY.csv.
Obtaining a hit rate of 390.494 hits over the 500.000 records of test file, what means 78.09%

In both procedures, the SUSY.csv margins to be considered as a test and training file are parameterized. In advance, only AssignClassWithSusyWeighted_v1.bat wil be executed, changing the parameters at will.

In AssignClassWithSusyWeighted.bat you can change C:\SUSY.csv for any test file that has a structure analogous to SUSY obtaining the result with the classes assigned in the FileTextWithClassAsigned.txt file

For example: testing SUSY20.txt, attached, which is a file with 20 SUSY records, using the AssignClassWithSusyWeighted_v1_Test_SUSY20.bat procedure, which is also attached, an error rate of 16 successful records and 4 errors is obtained.

Testing with the attached SUSY20Bad.txt file, which is the same SUSY20.txt to which the class of the first three records has been changed, and executing the AssignClassWithSusyWeighted_v1_Test_SUSY20bad.bat procedure. It is observed that the 3 errors introduced appear reflected, showing a rate of 13 correct answers and 7 errors. This tests the sensitivity of the model.

### Cite this software as:
 ** Alfonso Blanco García ** SUSY_WEIGHTED_V1

More detailed description:

The main difference in this model comparing with others is that each field is weihgted separately. In other models weights are identical for all fields.

It begins with a statistical approach: each value of the first 8 significant fields (following suggestion from the SUSY download page, only these fields are considered as the rest are the result of complex calculations from the first 8 original fields) is associated with an index.

To associate this index to each value of each field, the most suitable width of the samples for each field has been previously estimated.

The frequencies of each field are established in each index that represents a sampling width.

Once the frequencies for each value of each field have been established, the probability of the record belonging to class 0 or 1 is calculated using the Naive Nayes method, given the independence between the values of each field, having verified that the results are better than those obtained with other methods

In the preparation of Susy Weighted, the suggestion indicated in the textbook Artificial Intelligence A Modern Approach, Stuart Rosell, Peter Norvig (Third edition), page 749 and following, regarding the weighted training set, has been followed.

With one caveat, and that is that it has been impossible to achieve an accuracy of 83% in a first step from which, with the subsequent production of valued susy files, an accuracy of 90% could be reached, this was achieved in a previous project that was unsuccessful.
due to the excessive adaptation to the training file, but which then turned out to be detrimental when applied to a different test file.

It is pending to optimize in later versions

References:

https://archive.ics.uci.edu/ml/datasets/SUSY

Artificial Intelligence A Modern Approach, Stuart Rosell, Peter Norvig (Third edition), page 749 and following referring to weighted training sets.

Regarding accuracy:

1. The results offered on the website: http://physics.bu.edu/~pankajm/ML-Notebooks/HTML/NB5_CVII-logreg_SUSY.html

2. "Using Pyspark Environment for Solving a Big Data Problem: Searching for Supersymmetric Particles"
Mourad Azhari, Abdallah Abarda, Badia Ettaki, Jamal Zerouaoui, Mohamed Dakkon
International Journal of Innovative Technology and Exploring Engineering (IJITEE)
ISSN: 2278-3075, Volume-9 Issue-7, May 2020
https://www.researchgate.net/publication/341301008_Using_Pyspark_Environment_for_Solving_a_Big_Data_Problem_Searching_for_Supersymmetric_Particles
