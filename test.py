from keras import layers
from keras import models
from keras import activations

#Keras Model:
input = keras.Input(shape=null)

var_0 = layers.Dense(OUTPUT_DIMENSION, ACTIVATION_FUNCTION) (input) #Name:Dense, ID:87c30d7c-aea2-453b-b0e0-2f0bb576435c
var_1 = layers.Dense(OUTPUT_DIMENSION, ACTIVATION_FUNCTION) (var_0, var_3) #Name:Dense, ID:d7322b10-9657-4dee-acb0-e9d78e220f94
var_2 = layers.Output(OUTPUT_DIMENSION)) (var_1) #Name:Output, ID:output
var_3 = layers.Dense(OUTPUT_DIMENSION, ACTIVATION_FUNCTION) (input) #Name:Dense, ID:19fa9e0d-3aec-4125-a172-a4a38b840482

var_3.summary();
