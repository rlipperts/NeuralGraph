from keras import layers
from keras import models
from keras import activations

#Keras Model:
input = keras.Input(shape=null)


var_0_input = keras.Input(shape=(3, 3))
var_0_0 = layers.Dense((3, 1), activation=Softsign) (var_0_input)  #NAME:Dense, ID:885a0af4-31c0-4f88-8716-40e564988626
var_0_output= layers.Flatten() (var_0_0)  #NAME:Flatten, ID:81648bf5-8f4c-4598-84c7-b609eb0e2da0
var_0_model = Model(var_0_input, var_0_output)
output = var_0_model (input)  #NAME:test, ID:a313f518-5906-4c1c-9d3a-03ed5044ce68


model = Model(input, output
output.summary();