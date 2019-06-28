import pandas as pd
import numpy as np
import pyduke.common.core_util as cu
import maga.ml.util as util

# Series - Homogeneous data
# -------------------------
sName = pd.Series(data=["Raghu", "Madhu", "Pavi", "Hari", "Sanjeev"], name="Name")
sMark = pd.Series(data=[80, 90, 85, 75, 78]                         , name="Mark")

# Access Series like a list
# Note: zip helps iterate over multiple iterators at the same time, together!
for x, title in zip ((sName, sName[0], sName[1:3]), ("Series", "Series Element", "Series Range")):
    util.show_title_data(title, x)

# Dataframe - Matrix of data where each column is a pd.Series
# -----------------------------------------------------------

# Create dataframe from series
df = pd.concat([sName, sMark], axis=1)
util.show_title_data("DataFrame from series concat", df)

# Create a dataframe from numpy
A = np.random.randint(1, 100, size=(3,5))
df_matrix = pd.DataFrame(A, columns=['A','B','C','D','E'])
util.show_title_data("DataFrame from numpy", df_matrix)

# Create a dataframe from dict
d = {"Name":["Raghu", "Madhu", "Pavi", "Hari", "Sanjeev"], "Mark":[80, 90, 85, 75, 78]}
df = pd.DataFrame(data=d)
util.show_title_data("Dataframe from dict", df)

# Select subset of columns from dataframe
util.show_title_data("Subset of columns from dataframe", df_matrix[['A','D']])

# Add column to dataframe
df_matrix['F'] = [10, 11, 12]
util.show_title_data("Add column to dataframe", df_matrix)

# Remove column from dataframe
df_matrix.pop('E')
util.show_title_data("Del column in dataframe", df_matrix)
