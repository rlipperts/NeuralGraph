from keras import layers
from keras import models
from keras import activations

#Keras Model:
input = keras.Input(shape=(3, 3))

var_0 = layers.Dense((3, 1), activation=Softsign) (input)  #NAME:Dense, ID:885a0af4-31c0-4f88-8716-40e564988626
output = layers.Flatten() (var_0)  #NAME:Flatten, ID:81648bf5-8f4c-4598-84c7-b609eb0e2da0

output.summary();
