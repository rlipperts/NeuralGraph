from keras import layers
from keras import models
from keras import activations

#Keras Model:
input = keras.Input(shape=(2))

var_0 = layers.Flatten() (input)  #NAME:Flatten, ID:29c819fe-e715-4c39-bfd2-8c27c9f7d3b1
var_3 = layers.Flatten() (input)  #NAME:Flatten, ID:6fdad766-2ca7-4a12-a8a1-cc1bc7d14f48
var_5 = layers.Flatten() (input)  #NAME:Flatten, ID:30d9b2e5-202d-4b66-a2f7-611ed5b12cd5
var_1 = layers.Flatten() (var_0)  #NAME:Flatten, ID:d46db466-30e7-404c-8ea0-daebaedc8c2d
var_2 = layers.Flatten() (var_1)  #NAME:Flatten, ID:b005ea91-6e97-42e0-bf19-8a97d70b3426
var_4 = layers.Flatten() (var_3)  #NAME:Flatten, ID:705abd14-5ea4-4fcb-9910-d41e9d8448e4
var_6 = layers.Flatten() (var_5)  #NAME:Flatten, ID:3e82093a-c008-4d9a-8f62-e3a1f73742d4
output = layers.Add() ([var_2, var_4, var_6])  #NAME:name, ID:6b95ec53-07d1-4934-bdef-d4880b89c994

output.summary();
