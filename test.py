from keras import layers
from keras import models
from keras import activations

#Keras Model:
input = keras.Input(shape=null)


var_0_input = keras.Input(shape=null)
var_0_0 = layers.Flatten() (input)  #NAME:Flatten, ID:b71758dc-8a04-4ac3-aab6-4ad72a39e144

var_0_1_input = keras.Input(shape=(2, 1))
var_0_1_0 = layers.Dense((3, 5), activation=Selu) (input)  #NAME:Dense, ID:959a5b0f-b3ce-4bed-aeb2-9c993ddf5987
var_0_1_2 = layers.Embedding((5, 3), (2, 3)) (input)  #NAME:Embedding, ID:cbc401f7-29ec-40e3-8414-5b81ee35b658
var_0_1_4 = layers.Conv 1d((5, 3), 3, activation=Relu) (input)  #NAME:Conv 1d, ID:64880a80-a5e4-455d-a7f3-f2c2c99c6815
var_0_1_1 = layers.Flatten() (var_0_1_0)  #NAME:Flatten, ID:352a7f22-2451-47d6-8fee-d0d9abf5365c
var_0_1_3 = layers.Flatten() (var_0_1_2)  #NAME:Flatten, ID:dad3656a-ba5e-4dc8-87a8-9b4ad27d65b2
var_0_1_output = layers.Flatten() (var_0_1_4)  #NAME:Flatten, ID:f058ab4e-a9c7-4b79-928f-0684d11b8565
var_0_1_model = Model(var_0_1_input , var_0_1_output )
var_0_1 = var_0_1_model (var_0_0)  #NAME:TestGraph, ID:30797559-346b-4f30-bc9a-fcccb62bff38

var_0_output = layers.Flatten() (var_0_1)  #NAME:Flatten, ID:e07bbb69-1137-4ccc-a8f1-fd634186b40d
var_0_model = Model(var_0_input , var_0_output )
output = var_0_model (input)  #NAME:ExampleGraphWithImport, ID:1761d235-45b0-4d48-a987-d912b181aec1


output.summary();
