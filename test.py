from keras import layers
from keras import models
from keras import activations

#Keras Model:
input = keras.Input(shape=null)

var_0 = layers.Flatten() (input)  #NAME:Flatten, ID:370bc939-bbb3-4f8a-97d8-43fd1374fb49
var_2 = layers.Flatten() (input)  #NAME:Flatten, ID:d4516cfe-2cee-4a20-bd83-14e87601ba96
var_5 = layers.Embedding(null, null) (input)  #NAME:Embedding, ID:42c8a4eb-ee38-4273-bb24-78d6a8c41787
var_1 = layers.Max pooling 1d(pool_size=45543) (var_0)  #NAME:Max Pooling 1d, ID:2316337f-8f68-48e7-a80a-34be52c49311
var_3 = layers.Flatten() (var_2)  #NAME:Flatten, ID:aa283a2d-6c44-42c3-a994-f6387f82965c
var_4 = layers.Flatten() (var_3)  #NAME:Flatten, ID:25bfd057-43fe-470a-8c05-f87fe3164495
var_6 = layers.Conv 2d(null, (1, 3), activation=Sigmoid) (var_5)  #NAME:Conv 2d, ID:165ddf28-2cf1-40c4-a0e6-c1b2550371e8
output = layers.Add() ([var_1, var_4, var_6])  #NAME:Add, ID:a15c02a1-2352-4ee1-b1f3-9fdc38d0902e

output.summary();
