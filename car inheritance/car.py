'''
CS 115, Lab 12, Inheritance

Author: <your name here>
Pledge: <write pledge>
'''

'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
' Question 1 (15 points)
' Implement missing sections of the Car class.
'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
class Car(object):
    '''Write the constructor below. It should take in four arguments:
       - make (a string)
       - model (a string)
       - mpg (miles per gallaon, a float)
       - tank_capacity (capacity of the gas tank in gallons, a float)
       The fields must be private.
       7 points'''

    '''Write properties for mpg and tank_capacity. 2 points each'''
 

    '''Write setters for mpg and tank_capacity. 2 points each'''



    '''Write a method called get_total_range.
       It returns the total distance the car can travel on a full tank of
       gas.
       4 points'''

    def __str__(self):
        return self.__make + ' ' + self.__model + ', MPG: ' + str(self.__mpg) \
            + ', tank capacity: ' + str(self.__tank_capacity)

'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
' Question 2 (15 points)
' Implement missing sections of the HybridCar class. HybridCar should be a
' subclass of Car.
'''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''''
class HybridCar():  # 2 points
    '''Write the constructor below. It should take in 6 arguments:
    - the first four are the same as in the Car constructor
    - battery_kWh (battery power in kilowatt-hours, a float)
    - miles_per_kWh (miles per kilowatt-hours, a float)
    The additional fields must be private.
    5 points'''


    def get_battery_range(self):
        '''Returns the total distance the car can travel on a fully charged
        battery.
        4 points'''
 

    '''Override the method get_total_range.
    Returns the total distance the car can travel on a full tank of
    gas and a fully charged battery.
    Do not do any math here except a single +. To get credit, you must call
    the methods you have already written.
    4 points'''

    def __str__(self):
        return super().__str__() + ', battery kWh: ' + \
            str(self.__battery_kWh) + ', miles/kWh: ' + \
            str(self.__miles_per_kWh)
